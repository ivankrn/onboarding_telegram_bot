package com.ppteam.onboardingtelegrambot.service;

import com.ppteam.onboardingtelegrambot.controller.error.NotFoundException;
import com.ppteam.onboardingtelegrambot.database.Test;
import com.ppteam.onboardingtelegrambot.database.TestRepository;
import com.ppteam.onboardingtelegrambot.dto.*;
import com.ppteam.onboardingtelegrambot.dto.mappers.TestMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TestServiceImpl implements TestService {
    private final TestRepository testRepository;
    private final TestMapper testMapper;

    @Override
    public Page<TestDto> findAll(Pageable page) {
        return testRepository.findAll(page).map(testMapper::testToTestDto);
    }

    @Override
    public Page<TestDto> findByTopicId(long topicId, Pageable page) {
        return testRepository.findByTopicId(topicId, page).map(testMapper::testToTestDto);
    }

    @Override
    public TestDto findById(long id) {
        return testRepository.findById(id).map(testMapper::testToTestDto).orElseThrow(NotFoundException::new);
    }

    @Override
    public TestFullDto findByIdWithQuestionsAndAnswers(long id) {
        return testRepository.findByIdWithQuestionsAndAnswers(id).map(testMapper::testToTestFullDto)
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public long count() {
        return testRepository.count();
    }

    @Override
    public void create(TestCreateDto testCreateDto) {
        testRepository.save(testMapper.testCreateDtoToTest(testCreateDto));
    }

    @Override
    public void update(long id, TestUpdateDto testUpdateDto) {
        Test test = testRepository.findById(id).orElseThrow(NotFoundException::new);
        testMapper.updateTestFromDto(testUpdateDto, test);
        testRepository.save(test);
    }

    @Override
    public void updatePartial(long id, TestPatchDto testPatchDto) {
        Test test = testRepository.findById(id).orElseThrow(NotFoundException::new);
        testMapper.patchTestFromDto(testPatchDto, test);
        testRepository.save(test);
    }

    @Override
    public void deleteById(long id) {
        testRepository.deleteById(id);
    }
}
