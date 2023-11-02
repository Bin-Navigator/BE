package com.binnavigator.be.Bin;

import com.binnavigator.be.Bin.Data.*;
import com.binnavigator.be.Member.Member;
import com.binnavigator.be.Member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class BinService {
    private final BinRepository binRepository;
    private final MemberRepository memberRepository;

    public Long add(BinAddRequest binAddRequest) {
        Member owner = memberRepository.findById(binAddRequest.getUserId()).orElseThrow();
        Bin newBin = Bin.builder()
                .owner(owner)
                .information(binAddRequest.getInformation())
                .latitude(binAddRequest.getLatitude())
                .longitude(binAddRequest.getLongitude())
                .image(binAddRequest.getImage())
                .reported(0)
                .isFull(false)
                .build();
        return binRepository.save(newBin).getId();
    }

    public boolean delete(BinDeleteRequest binDeleteRequest) {
        Bin deleteBin = binRepository.findById(binDeleteRequest.getBinId()).orElseThrow();
        if(deleteBin.getOwner().getId() == binDeleteRequest.getUserId()) {
            binRepository.delete(deleteBin);
            return true;
        } else
            return false;
    }

    public void update(BinUpdateRequest binUpdateRequest) {
        binRepository.updateInformation(binUpdateRequest.getBinId(), binUpdateRequest.getInformation());
    }

    public void report(long binId) {
        Bin reportBin = binRepository.findById(binId).orElseThrow();
        reportBin.reported();
        if(reportBin.haveToDeleted())
            binRepository.delete(reportBin);
        else
            binRepository.save(reportBin);
    }

    public List<GetResponse> getAll() {
        List<GetResponse> getResponseList = new ArrayList<>();
        List<Bin> all = binRepository.findAll();
        for(Bin bin : all) {
            GetResponse getResponse = buildGetResponse(bin);
            getResponseList.add(getResponse);
        }
        return getResponseList;
    }

    public List<GetResponse> getByUserId(long userId) {
        List<GetResponse> getResponseList = new ArrayList<>();
        List<Bin> all = binRepository.findAll();
        for(Bin bin : all) {
            if(bin.getOwner().getId() == userId) {
                GetResponse getResponse = buildGetResponse(bin);
                getResponseList.add(getResponse);
            }
        }
        return getResponseList;
    }

    public GetByBinResponse getByBinId(long binId) {
        Bin findBin = binRepository.findById(binId).orElseThrow();
        return GetByBinResponse.builder()
                .binId(findBin.getId())
                .latitude(findBin.getLatitude())
                .longitude(findBin.getLongitude())
                .information(findBin.getInformation())
                .image(findBin.getImage())
                .build();
    }

    public GetResponse full(long binId) {
        Bin findBin = binRepository.findById(binId).orElseThrow();
        findBin.changeIsFull();
        binRepository.save(findBin);
        return buildGetResponse(findBin);
    }

    public GetResponse buildGetResponse(Bin bin) {
        return GetResponse.builder()
                .binId(bin.getId())
                .latitude(bin.getLatitude())
                .longitude(bin.getLongitude())
                .information(bin.getInformation())
                .isFull(bin.isFull())
                .build();
    }

    @Scheduled(cron = "0 0 6 * * ?", zone = "Asia/Seoul")
    public void setAllIsFullFalse() {
        binRepository.setAllIsFullFalse();
    }
}
