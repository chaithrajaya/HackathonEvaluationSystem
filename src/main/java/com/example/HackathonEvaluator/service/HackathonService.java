package com.example.HackathonEvaluator.service;

import com.example.HackathonEvaluator.dto.HackathonDto;
import com.example.HackathonEvaluator.entity.Hackathon;
import com.example.HackathonEvaluator.repository.HackathonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HackathonService {

    @Autowired
    private HackathonRepository hackathonRepository;

    public HackathonDto createHackathon(HackathonDto hackathonDto) {
        Hackathon hackathon = new Hackathon();
        hackathon.setName(hackathonDto.getName());
        hackathon.setDescription(hackathonDto.getDescription());
        hackathon.setStartDate(hackathonDto.getStartDate());
        hackathon.setEndDate(hackathonDto.getEndDate());
        hackathon.setCreatedByUserId(hackathonDto.getCreatedByUserId());

        Hackathon savedHackathon = hackathonRepository.save(hackathon);
        return mapToHackathonDto(savedHackathon);
    }

    public HackathonDto getHackathonById(Long id) {
        Hackathon hackathon = hackathonRepository.findByHackathonId(id)
                .orElseThrow(() -> new RuntimeException("Hackathon not found with id: " + id));
        return mapToHackathonDto(hackathon);
    }

    public List<HackathonDto> getAllHackathons() {
        return hackathonRepository.findAll().stream()
                .map(this::mapToHackathonDto)
                .collect(Collectors.toList());
    }

    public HackathonDto updateHackathon(Long id, HackathonDto hackathonDto) {
        Hackathon hackathon = hackathonRepository.findByHackathonId(id)
                .orElseThrow(() -> new RuntimeException("Hackathon not found with id: " + id));

        hackathon.setName(hackathonDto.getName());
        hackathon.setDescription(hackathonDto.getDescription());
        hackathon.setStartDate(hackathonDto.getStartDate());
        hackathon.setEndDate(hackathonDto.getEndDate());
        hackathon.setCreatedByUserId(hackathonDto.getCreatedByUserId());

        Hackathon updatedHackathon = hackathonRepository.save(hackathon);
        return mapToHackathonDto(updatedHackathon);
    }

    public void deleteHackathon(Long id) {
        Hackathon hackathon = hackathonRepository.findByHackathonId(id)
                .orElseThrow(() -> new RuntimeException("Hackathon not found with id: " + id));
        hackathonRepository.delete(hackathon);
    }

    private HackathonDto mapToHackathonDto(Hackathon hackathon) {
        HackathonDto hackathonDto = new HackathonDto();
        hackathonDto.setHackathonId(hackathon.getHackathonId());
        hackathonDto.setName(hackathon.getName());
        hackathonDto.setDescription(hackathon.getDescription());
        hackathonDto.setStartDate(hackathon.getStartDate());
        hackathonDto.setEndDate(hackathon.getEndDate());
        hackathonDto.setCreatedByUserId(hackathon.getCreatedByUserId());
        return hackathonDto;
    }
}
