package com.ssafy.model.service;

import com.ssafy.model.dto.novel.NovelResponseDto;
import com.ssafy.model.dto.novel.NovelSaveRequestDto;
import com.ssafy.model.dto.novel.NovelUpdateRequestDto;
import com.ssafy.model.entity.Genre;
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

import java.time.LocalDate;
import java.util.ArrayList;
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
    NovelGenreRepository ngRepo;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public Page<NovelResponseDto> getNovels(Pageable pageable, String sort) {
        PageRequest pageRequest
                = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
                Sort.by("novelUpdatedAt").descending());

        Page<Novel> novelEntityPage =nRepo.findAll(pageable);
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


            String[] words = novelName.split(" ");
            for (String word : words) {
                Search search = new Search();
                search.setMember(member);
                search.setSearchWord(word);
                search.setSearchCreatedAt(LocalDate.now());
                sRepo.save(search);
            }
        }

        Page<Novel> novelEntityPage = nRepo.find("novel_name", novelName, pageRequest, sort);
        Page<NovelResponseDto> novels = novelEntityPage.map(novelEntity -> new NovelResponseDto(novelEntity));

        return novels;
    }

    @Override
    public Page<NovelResponseDto> getNovlesByNick(String memNick, Pageable pageable, String sort, int memPk) {
        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("novelUpdatedAt").descending());

        if (memPk != 0) {
            Member member = mRepo.findById(memPk)
                    .orElseThrow(() -> new IllegalArgumentException("member pk :  " + memPk + "가 존재하지 않습니다."));


            String[] words = memNick.split(" ");
            for (String word : words) {
                Search search = new Search();
                search.setMember(member);
                search.setSearchWord(word);
                search.setSearchCreatedAt(LocalDate.now());
                sRepo.save(search);
            }
        }

        Page<Novel> novelEntityPage = nRepo.find("author_name", memNick, pageRequest, sort);
        Page<NovelResponseDto> novels = novelEntityPage.map(novelEntity -> new NovelResponseDto(novelEntity));

        return novels;
    }

    @Override
    public Page<NovelResponseDto> getNovelsByNameOrNick(String word, Pageable pageable, String sort, int memPk) {
        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("novelUpdatedAt").descending());

        if (memPk != 0) {
            Member member = mRepo.findById(memPk)
                    .orElseThrow(() -> new IllegalArgumentException("member pk :  " + memPk + "가 존재하지 않습니다."));


            String[] words = word.split(" ");
            for (String word_ : words) {
                Search search = new Search();
                search.setMember(member);
                search.setSearchWord(word_);
                search.setSearchCreatedAt(LocalDate.now());
                sRepo.save(search);
            }
        }

        Page<Novel> novelEntityPage = nRepo.find("author_or_novel", word, pageRequest, sort);
        Page<NovelResponseDto> novels = novelEntityPage.map(novelEntity -> new NovelResponseDto(novelEntity));

        return novels;
    }

    @Override
    public void registNovel(NovelSaveRequestDto requestDto) {
        nRepo.save(requestDto.toEntity(mRepo));
    }

    @Override
    public NovelResponseDto updateNovel(int novelPk, NovelUpdateRequestDto requestDto) {
        Novel novelEntity = nRepo.findById(novelPk).orElseThrow(() ->
                new IllegalArgumentException("novel pk :  " + novelPk + "가 존재하지 않습니다."));

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

    @Override
    public void deleteNovel(int novelPk) {
        nRepo.deleteById(novelPk);
    }

    @Override
    public List<NovelResponseDto> getNovelsByGenre(int genrePk) {
        Genre genre = gRepo.findById(genrePk).orElseThrow(() ->
                new IllegalArgumentException("genre pk :  " + genrePk + "가 존재하지 않습니다."));


        List<NovelResponseDto> novels =
                genre.getNovels().stream().map(novelEntity -> new NovelResponseDto(novelEntity))
                        .collect(Collectors.toList());

        return novels;
    }

    @Override
    public void updateGenreOfNovel(int novelPk, List<Integer> genrePks) {
        Novel novel = nRepo.findById(novelPk).orElse(null);
        List<Genre> oGenres = novel.getGenres(); // 원래 장르들
        // 새로운 장로들
        List<Genre> uGenres = genrePks.stream().map(genrePk -> {
            Genre genre = gRepo.findById(genrePk).orElse(null);
            if (oGenres.contains(genre)) {
                // 넘어가고
            } else {
                // 추가
                oGenres.add(genre);
                genre.getNovels().add(novel);
            }
            return genre;
        }).collect(Collectors.toList());

        List<Genre> removeGenres = new ArrayList<Genre>();
        for (Genre genre : oGenres) {
            if (uGenres.contains(genre)) {
                // 넘어가고
            } else {
                // 삭제
                genre.getNovels().remove(novel);
                removeGenres.add(genre);
            }
        }
        for (Genre genre : removeGenres) {
            oGenres.remove(genre);
        }
        nRepo.save(novel);
    }

    @Override
    public Page<NovelResponseDto> getNovelByMember(int memPk, Pageable pageable, String sort) {
        Page<Novel> novelEntityPage = nRepo.find("mem_pk", Integer.toString(memPk), pageable, sort);
        Page<NovelResponseDto> novels = novelEntityPage.map(novelEntity -> new NovelResponseDto(novelEntity));

        return novels;
    }

}
