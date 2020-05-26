package com.ssafy.model.service;

import com.ssafy.model.dto.novel.NovelResponseDto;
import com.ssafy.model.dto.novel.NovelSaveRequestDto;
import com.ssafy.model.dto.novel.NovelUpdateRequestDto;
import com.ssafy.model.entity.*;
import com.ssafy.model.repository.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
public class NovelServiceImpl implements NovelService {
    @Autowired
    HashTagService hashTagService;

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
        Member member = mRepo.findById(requestDto.getMemberPk()).orElseThrow(() ->
                new MemberException(MemberException.NOT_EXIST));

        List<Genre> genres = requestDto.getGenrePks().stream().map(genrePk ->
                gRepo.findById(genrePk).orElseThrow(() ->
                        new GenreException(GenreException.NOT_EXIST))).collect(Collectors.toList());
        List<HashTag> hashTags = requestDto.getHashTagStrs().stream().map(hashTagStr ->
                hashTagService.findOrRegistHashTag(hashTagStr)).collect(Collectors.toList());

    	Novel novel = nRepo.save(requestDto.toEntity(member, genres, hashTags));

        // 장르 추가
        for(Genre genre : genres){
            genre.getNovels().add(novel);
        }

        // 해시태그 추가
        for(HashTag hashTag : hashTags){
            hashTag.getNovels().add(novel);
        }

        NovelResponseDto responseDto = new NovelResponseDto(novel);
        return responseDto;
    }
    
    @Override
    public NovelResponseDto updateNovel(int novelPk, NovelUpdateRequestDto requestDto) {
        Novel novel = nRepo.findById(novelPk).orElseThrow(() ->
                new NovelException(NovelException.NOT_EXIST));

        List<Genre> genres = requestDto.getGenrePks().stream().map(genrePk ->
                gRepo.findById(genrePk).orElseThrow(() ->
                        new GenreException(GenreException.NOT_EXIST))).collect(Collectors.toList());
        List<HashTag> hashtags = requestDto.getHashTagStrs().stream().map(hashTagStr ->
                hashTagService.findOrRegistHashTag(hashTagStr)).collect(Collectors.toList());

        novel.update(
                genres,
                hashtags,
                requestDto.getNovelName(),
                requestDto.getNovelIntro(),
                requestDto.getNovelImage(),
                requestDto.isNovelLimit(),
                requestDto.isNovelOpen(),
                requestDto.getNovelStatus(),
                requestDto.isNovelOnly()
        );
        
        nRepo.save(novel);
        NovelResponseDto responseDto = new NovelResponseDto(novel);
        
        return responseDto;
    }

    @Override
    public void deleteNovel(int novelPk) {
        Novel novel = nRepo.findById(novelPk)
                .orElseThrow(() -> new NovelException(NovelException.NOT_EXIST));

        deleteNovel(novel);
    }

    @Override
    public Page<NovelResponseDto> getNovelsByGenre(int genrePk, Pageable pageable, String sort) {
        PageRequest pageRequest = getPageRequest(pageable, sort);

        Genre genre = gRepo.findById(genrePk)
                .orElseThrow(() -> new GenreException(GenreException.NOT_EXIST));
        Page<Novel> novelPage = nRepo.findByGenresLike(genre, pageRequest);
        Page<NovelResponseDto> novelDtoPage = novelPage.map(novel -> new NovelResponseDto(novel));
        return novelDtoPage;
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

    public void deleteNovel(Novel novel){
        novel.beforeDelete();
        nRepo.save(novel);
        nRepo.delete(novel);
    }

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
    
    public Page<NovelResponseDto> getFeelNovels(int type, Pageable pageable, String sort, int genrePk) {
    	PageRequest pageRequest = getPageRequest(pageable, sort);
    	
    	Page<Novel> novelEntityPage;
    	if(genrePk != 0) {
            Genre genre = gRepo.findById(genrePk).orElse(null);

            if(genre == null) novelEntityPage = nRepo.findByFeel(type, pageRequest);
            else novelEntityPage = nRepo.findByFeelAndGenre(type, pageRequest, genrePk);
        }
        else novelEntityPage = nRepo.findByFeel(type, pageRequest);
    	
    	Page<NovelResponseDto> novels = novelEntityPage.map(novelEntity -> new NovelResponseDto(novelEntity));
        
    	return novels;
    }
    
    public PageRequest getPageRequest(Pageable pageable, String sort) {
    	if(sort == null) sort = "updated";
    	
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
        
        PageRequest pageRequest
            = PageRequest.of(pageable.getPageNumber() == 0 ? 0 : pageable.getPageNumber() - 1, pageable.getPageSize(),
                sort.equals("updated") ? Sort.by("novelUpdatedAt").descending() :
                    Sort.by(sort, "novelUpdatedAt").descending());
        
        return pageRequest;
    }

}
