package com.ppteam.onboardingtelegrambot.service;

import com.ppteam.onboardingtelegrambot.controller.error.NotFoundException;
import com.ppteam.onboardingtelegrambot.database.TestRepository;
import com.ppteam.onboardingtelegrambot.dto.TestDto;
import com.ppteam.onboardingtelegrambot.dto.TestFullDto;
import com.ppteam.onboardingtelegrambot.dto.mappers.MapStructMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TestServiceImpl implements TestService {
    private final TestRepository testRepository;
    private final MapStructMapper mapStructMapper;

    @Override
    public Page<TestDto> findAll(Pageable page) {
        return testRepository.findAll(page).map(mapStructMapper::testToTestDto);
    }

    @Override
    public Page<TestDto> findByTopicId(long topicId, Pageable page) {
        return testRepository.findByTopicId(topicId, page).map(mapStructMapper::testToTestDto);
    }

    @Override
    public TestFullDto findById(long id) {
        return testRepository.findById(id).map(mapStructMapper::testToTestFullDto)
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public long count() {
        return testRepository.count();
    }

    @Override
    public void save(TestFullDto testFullDto) {
        testRepository.save(mapStructMapper.testFullDtoToTest(testFullDto));
    }

    @Override
    public void deleteById(long id) {
        testRepository.deleteById(id);
    }
}
