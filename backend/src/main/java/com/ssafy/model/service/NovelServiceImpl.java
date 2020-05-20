package com.ssafy.model.service;

import com.ssafy.model.dto.novel.NovelResponseDto;
import com.ssafy.model.dto.novel.NovelSaveRequestDto;
import com.ssafy.model.dto.novel.NovelUpdateRequestDto;
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
	NovelGenreRepository ngRepo;
    @Autowired
    HashTagRepository hRepo;

    @Autowired
	ModelMapper modelMapper;

    @Override
    public Page<NovelResponseDto> getNovels(Pageable pageable, String sort) {
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

        Page<Novel> novelEntityPage = nRepo.findAll(pageRequest);
        Page<NovelResponseDto> novels = novelEntityPage.map(novelEntity -> new NovelResponseDto(novelEntity));

        return novels;
    }

    @Override
    public NovelResponseDto getNovel(int novelPk) {
        Novel novelEntity = nRepo.findById(novelPk).orElseThrow(() ->
                new IllegalArgumentException("novel pk :  " + novelPk + "가 존재하지 않습니다."));

        System.out.println(novelEntity.getGenres());
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

        Page<Novel> novelEntityPage = nRepo.find("author_or_novel", word, pageRequest, sort);
        Page<NovelResponseDto> novels = novelEntityPage.map(novelEntity -> new NovelResponseDto(novelEntity));

        return novels;
    }

    @Override
    public void registNovel(NovelSaveRequestDto requestDto) {
    	Novel novel = requestDto.toEntity(mRepo);
    	List<Integer> genreList = requestDto.getGenres();
    	
    	for(int genrePk : genreList) {
    		Genre genre = gRepo.findById(genrePk).orElse(null);
    		genre.getNovels().add(novel);
    		novel.getGenres().add(genre);
    	}
    	
    	List<String> hashTagList = requestDto.getHashTags();
    	
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
        
        Member member = mRepo.findById(memPk).orElseThrow(() ->
        	new IllegalArgumentException("mem pk :  " + memPk + "가 존재하지 않습니다."));
        
        Page<Novel> novelEntityPage = nRepo.findByMember(member, pageable);
        Page<NovelResponseDto> novels = novelEntityPage.map(novelEntity -> new NovelResponseDto(novelEntity));

        return novels;
    }

}
