package com.reddot.stats.service.impl;

import com.reddot.stats.domain.StatisticsPost;
import com.reddot.stats.pojo.PostInfo;
import com.reddot.stats.repository.StatisticsPostRepository;
import com.reddot.stats.service.StatisticsPostService;
import com.reddot.stats.service.dto.StatisticsPostDTO;
import com.reddot.stats.service.mapper.StatisticsPostMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link StatisticsPost}.
 */
@Service
@Transactional
public class StatisticsPostServiceImpl implements StatisticsPostService {

    private final Logger log = LoggerFactory.getLogger(StatisticsPostServiceImpl.class);

    private final StatisticsPostRepository statisticsPostRepository;

    private final StatisticsPostMapper statisticsPostMapper;

    public StatisticsPostServiceImpl(StatisticsPostRepository statisticsPostRepository, StatisticsPostMapper statisticsPostMapper) {
        this.statisticsPostRepository = statisticsPostRepository;
        this.statisticsPostMapper = statisticsPostMapper;
    }
    
    
    @Override
    public List<PostInfo> stasticsByUserAndDate() {
        log.debug("Request to get Statistics for Post by User and Date");
        return statisticsPostRepository.findAllforTest();
    }

    @Override
    public StatisticsPostDTO save(StatisticsPostDTO statisticsPostDTO) {
        log.debug("Request to save StatisticsPost : {}", statisticsPostDTO);
        StatisticsPost statisticsPost = statisticsPostMapper.toEntity(statisticsPostDTO);
        statisticsPost = statisticsPostRepository.save(statisticsPost);
        return statisticsPostMapper.toDto(statisticsPost);
    }

    @Override
    public Optional<StatisticsPostDTO> partialUpdate(StatisticsPostDTO statisticsPostDTO) {
        log.debug("Request to partially update StatisticsPost : {}", statisticsPostDTO);

        return statisticsPostRepository
            .findById(statisticsPostDTO.getId())
            .map(existingStatisticsPost -> {
                statisticsPostMapper.partialUpdate(existingStatisticsPost, statisticsPostDTO);

                return existingStatisticsPost;
            })
            .map(statisticsPostRepository::save)
            .map(statisticsPostMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<StatisticsPostDTO> findAll() {
        log.debug("Request to get all StatisticsPosts");
        return statisticsPostRepository
            .findAll()
            .stream()
            .map(statisticsPostMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<StatisticsPostDTO> findOne(Long id) {
        log.debug("Request to get StatisticsPost : {}", id);
        return statisticsPostRepository.findById(id).map(statisticsPostMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete StatisticsPost : {}", id);
        statisticsPostRepository.deleteById(id);
    }
}
