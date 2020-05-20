package com.ssafy.model.service;

import com.ssafy.model.dto.novel.NovelResponseDto;
import com.ssafy.model.dto.novel.NovelSaveRequestDto;
import com.ssafy.model.dto.novel.NovelUpdateRequestDto;
import com.ssafy.model.entity.*;
import com.ssafy.model.repository.GenreRepository;
import com.ssafy.model.repository.MemberRepository;
import com.ssafy.model.repository.NovelRepository;
import com.ssafy.model.repository.SearchRepository;
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
//    @Autowired
//    NovelGenreRepository ngRepo;

    @Autowired
	ModelMapper modelMapper;

    @Override
    public Page<NovelResponseDto> getNovels(Pageable pageable, String sort) {
        PageRequest pageRequest
                = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
                Sort.by("novelUpdatedAt").descending());

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

    @Override
    public Page<NovelResponseDto> getNovelsByName(String novelName, Pageable pageable, String sort, int memPk) {
        PageRequest pageRequest
                = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
                Sort.by("novelUpdatedAt").descending());

        if (memPk != 0) {
            Member member = mRepo.findById(memPk)
                    .orElseThrow(() -> new IllegalArgumentException("member pk :  " + memPk + "가 존재하지 않습니다."));

            Date date = new Date();
            String[] words = novelName.split(" ");
            for (String word : words) {
                Search search = new Search();
                search.setMember(member);
                search.setSearchWord(word);
                search.setSearchCreatedAt(date);
                sRepo.save(search);
            }
        }

//        Page<Novel> novelEntityPage = nRepo.find("novel_name", novelName, pageRequest, sort);
//        Page<NovelResponseDto> novels = novelEntityPage.map(novelEntity -> new NovelResponseDto(novelEntity));
//
//        return novels;
        return null;
    }

    @Override
    public Page<NovelResponseDto> getNovlesByNick(String memNick, Pageable pageable, String sort, int memPk) {
        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("novelUpdatedAt").descending());

        if (memPk != 0) {
            Member member = mRepo.findById(memPk)
                    .orElseThrow(() -> new IllegalArgumentException("member pk :  " + memPk + "가 존재하지 않습니다."));

            Date date = new Date();
            String[] words = memNick.split(" ");
            for (String word : words) {
                Search search = new Search();
                search.setMember(member);
                search.setSearchWord(word);
                search.setSearchCreatedAt(date);
                sRepo.save(search);
            }
        }

//        Page<Novel> novelEntityPage = nRepo.find("author_name", memNick, pageRequest, sort);
//        Page<NovelResponseDto> novels = novelEntityPage.map(novelEntity -> new NovelResponseDto(novelEntity));
//
//        return novels;
        return null;
    }

    @Override
    public Page<NovelResponseDto> getNovelsByNameOrNick(String word, Pageable pageable, String sort, int memPk) {
        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("novelUpdatedAt").descending());

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

//        Page<Novel> novelEntityPage = nRepo.find("author_or_novel", word, pageRequest, sort);
//        Page<NovelResponseDto> novels = novelEntityPage.map(novelEntity -> new NovelResponseDto(novelEntity));
//
//        return novels;
        return null;
    }

    @Override
    public NovelResponseDto registNovel(NovelSaveRequestDto requestDto) {
        Member member = mRepo.findById(requestDto.getMemberPk()).orElseThrow(() -> new MemberException(MemberException.NOT_EXIST));
    	Novel novelEntity = nRepo.save(requestDto.toEntity(member));
        NovelResponseDto novel = new NovelResponseDto(novelEntity);
        return novel;
//		Novel registN = modelMapper.map(novel, Novel.class);
//		Novel registedN = nRepo.save(registN);
//
//		List<String> genres = novel.getGenreList();
//
//		for(String g : genres) {
//			Genre genre = gRepo.findByGenreName(g);
//
//			if(genre == null) continue;
//
//			NovelGenre ng = new NovelGenre();
//			ng.setNovel(registedN);
//			ng.setGenre(genre);
//
//			ngRepo.save(ng);
//		}
//
//		List<String> hashTags = novel.getHashTagList();
//		for(String h : hashTags) {
//			HashTag hashTag = hRepo.findByHashTagName(h);
//
//			if(hashTag == null) {
//				HashTag addHashTag = new HashTag();
//				addHashTag.setHashTagName(h);
//				hashTag = hRepo.save(addHashTag);
//			}
//
//			NovelHashTag nh = new NovelHashTag();
//			nh.setNovel(registedN);
//			nh.setHashtag(hashTag);
//
//			nhRepo.save(nh);
//		}
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
//        Page<Novel> novelEntityPage = nRepo.find("mem_pk", Integer.toString(memPk), pageable, sort);
//        Page<NovelResponseDto> novels = novelEntityPage.map(novelEntity -> new NovelResponseDto(novelEntity));
//
//        return novels;
        return null;
    }

}
