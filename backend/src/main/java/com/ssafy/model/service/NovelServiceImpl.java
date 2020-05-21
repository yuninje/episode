package com.ssafy.model.service;

import com.ssafy.model.dto.novel.NovelResponseDto;
import com.ssafy.model.dto.novel.NovelSaveRequestDto;
import com.ssafy.model.dto.novel.NovelUpdateRequestDto;
import com.ssafy.model.entity.*;
import com.ssafy.model.repository.GenreRepository;
import com.ssafy.model.repository.MemberRepository;
import com.ssafy.model.repository.NovelRepository;
import com.ssafy.model.repository.SearchRepository;
import com.ssafy.model.entity.Genre;
import com.ssafy.model.entity.HashTag;
import com.ssafy.model.entity.Member;
import com.ssafy.model.entity.Novel;
import com.ssafy.model.entity.Search;
import com.ssafy.model.repository.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NovelServiceImpl implements NovelService {
    @Autowired
	NovelRepository nRepo;
    @Autowired
	MemberRepository mRepo;
    @Autowired
	GenreRepository gRepo;
    @Autowired
	SearchRepository sRepo;
    @Autowired
    HashTagRepository hRepo;

    @Autowired
	ModelMapper modelMapper;

    @Override
    public Page<NovelResponseDto> getNovels(Pageable pageable, String sort) {
        PageRequest pageRequest = getPageRequest(pageable, sort);

        Page<Novel> novelEntityPage = nRepo.findAll(pageRequest);
        Page<NovelResponseDto> novels = novelEntityPage.map(novelEntity -> new NovelResponseDto(novelEntity));

        return novels;
    }

    @Override
    public NovelResponseDto getNovel(int novelPk) {
        Novel novelEntity = nRepo.findById(novelPk).orElseThrow(() ->
                new IllegalArgumentException("novel pk :  " + novelPk + "가 존재하지 않습니다."));

        NovelResponseDto novel = new NovelResponseDto(novelEntity);
        return novel;
    }
    
    public Page<NovelResponseDto> getNovelsBySearchWord(String type, String word, Pageable pageable, String sort, int memPk, int genrePk) {
        PageRequest pageRequest = getPageRequest(pageable, sort);

        if (memPk != 0) {
            Member member = mRepo.findById(memPk)
                    .orElseThrow(() -> new IllegalArgumentException("member pk :  " + memPk + "가 존재하지 않습니다."));

            Date date = new Date();
            String[] words = word.split(" ");
            for (String word_ : words) {
                Search search = new Search();
                search.setMember(member);
                search.setSearchWord(word_);
                search.setSearchCreatedAt(date);
                sRepo.save(search);
            }
        }

        Page<Novel> novelEntityPage;
        if(genrePk != 0) {
            Genre genre = gRepo.findById(genrePk).orElse(null);

            if(genre == null) novelEntityPage = nRepo.findBySearchWord(type, word, pageRequest);
            else novelEntityPage = nRepo.findBySearchWordAndGenre(type, word, pageRequest, genrePk);
        }
        else novelEntityPage = nRepo.findBySearchWord(type, word, pageRequest);

        Page<NovelResponseDto> novels = novelEntityPage.map(novelEntity -> new NovelResponseDto(novelEntity));;

        return novels;
    }

    @Override
    public NovelResponseDto registNovel(NovelSaveRequestDto requestDto) {
        Member member = mRepo.findById(requestDto.getMemberPk()).orElseThrow(() -> new MemberException(MemberException.NOT_EXIST));
    	Novel novel = nRepo.save(requestDto.toEntity(member));

        List<Integer> genreList = requestDto.getGenres();

        // 장르 추가
        for(int genrePk : genreList) {
            Genre genre = gRepo.findById(genrePk).orElse(null);
            genre.getNovels().add(novel);
            novel.getGenres().add(genre);
        }

        List<String> hashTagList = requestDto.getHashTags();

        // 없는 해시태그에 대해서 추가.
        for(String hashTagName : hashTagList) {
            HashTag hashTag = hRepo.findByHashTagName(hashTagName);

            if(hashTag == null) {
                HashTag addHashTag = new HashTag();
                addHashTag.setHashTagName(hashTagName);
                hashTag = hRepo.save(addHashTag);
            }

            hashTag.getNovels().add(novel);
            novel.getHashTags().add(hashTag);
        }

        nRepo.save(novel);

        NovelResponseDto responseDto = new NovelResponseDto(novel);
        return responseDto;
    }

    @Override
    public NovelResponseDto updateNovel(int novelPk, NovelUpdateRequestDto requestDto) {
        Novel novelEntity = nRepo.findById(novelPk).orElseThrow(() ->
                new NovelException(NovelException.NOT_EXIST));

        novelEntity.update(
                requestDto.getNovelName(),
                requestDto.getNovelIntro(),
                requestDto.getNovelImage(),
                requestDto.isNovelLimit(),
                requestDto.isNovelOpen(),
                requestDto.getNovelStatus(),
                requestDto.isNovelOnly()
        );
        NovelResponseDto novel = new NovelResponseDto(nRepo.save(novelEntity));
        return novel;
    }

    @Transactional
    @Override
    public void deleteNovel(int novelPk) {
        Novel novel = nRepo.findById(novelPk)
                .orElseThrow(() -> new NovelException(NovelException.NOT_EXIST));

        // like_novels 테이블의 해당 소설의 좋아요 삭제
        for (Member member : novel.getLikedMembers()) {
            member.unLikeNovel(novel);
        }
        // novel_genre 테이블의 해당 소설의 장르 삭제
        for (Genre genre : novel.getGenres()){
            genre.removeGenreAtNovel(novel);
        }
        nRepo.save(novel);
        nRepo.deleteById(novelPk);
    }

    @Override
    public List<NovelResponseDto> getNovelsByGenre(int genrePk) {   // 이거 바꿔야할듯
        Genre genre = gRepo.findById(genrePk)
                .orElseThrow(() -> new GenreException(GenreException.NOT_EXIST));

        List<NovelResponseDto> novels =
                genre.getNovels().stream().map(novelEntity -> new NovelResponseDto(novelEntity))
                        .collect(Collectors.toList());

        return novels;
    }

    @Override
    public NovelResponseDto updateGenreOfNovel(int novelPk, List<Integer> genrePks) {
        Novel novelEntity = nRepo.findById(novelPk)
                .orElseThrow(() -> new NovelException(NovelException.NOT_EXIST));
        List<Genre> oGenres = novelEntity.getGenres(); // 원래 장르들
        // 새로운 장로들
        List<Genre> uGenres = genrePks.stream().map(genrePk ->
                gRepo.findById(genrePk).orElseThrow(() -> new GenreException(GenreException.NOT_EXIST)))
                .collect(Collectors.toList());

        // uGenres 중에서 oGenre에 없는것을 추가
        for(Genre genreEntity : uGenres){
            if(!oGenres.contains(genreEntity)){
                novelEntity.belongGenre(genreEntity);
            }
        }

        List<Genre> removeGenres = new ArrayList<>();
        // oGenres 중에서 uGenre에 없는것을 삭제
        for(Genre genreEntity : oGenres){
            if(! uGenres.contains(genreEntity)){
                removeGenres.add(genreEntity);
            }
        }

        for(Genre genre : removeGenres){
            novelEntity.notBelongGenre(genre);
        }

        NovelResponseDto novel = new NovelResponseDto(nRepo.save(novelEntity));
        return novel;
    }

    @Override
    public Page<NovelResponseDto> getNovelByMember(int memPk, Pageable pageable, String sort) {
        PageRequest pageRequest = getPageRequest(pageable, sort);

        Member member = mRepo.findById(memPk).orElseThrow(() ->
                new IllegalArgumentException("mem pk :  " + memPk + "가 존재하지 않습니다."));

        Page<Novel> novelEntityPage = nRepo.findByMember(member, pageRequest);
        Page<NovelResponseDto> novels = novelEntityPage.map(novelEntity -> new NovelResponseDto(novelEntity));
        return novels;
    }

    @Transactional
    public void deleteNovel(Novel novel){
        novel.beforeDelete();
        nRepo.save(novel);
        nRepo.delete(novel);
    }

    @Transactional
    public void deleteAllNovel() {
        List<Novel> novelList = nRepo.findAll();
        novelList.forEach(novel -> deleteNovel(novel));
    }

    public List<NovelResponseDto> getTop100(){
    	List<Novel> novelEntityList = nRepo.findTop100ByOrderByNovelViewDesc();
    	List<NovelResponseDto> novels = novelEntityList.stream().map(novelEntity -> new NovelResponseDto(novelEntity))
                .collect(Collectors.toList());
    	
    	return novels;
    }
    
    public PageRequest getPageRequest(Pageable pageable, String sort) {
    	if(sort != null) {
    		switch(sort) {
    		case "likes":
    			sort = "novelLikes";
    			break;
    		case "recommends":
    			sort = "novelRecommends";
    			break;
    		case "view":
    			sort = "novelView";
    			break;
    		}
    	}
    	
        PageRequest pageRequest
                = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
                	sort == null ? Sort.by("novelUpdatedAt").descending() :
                		Sort.by(sort, "novelUpdatedAt").descending());
        
        return pageRequest;
    }
}
