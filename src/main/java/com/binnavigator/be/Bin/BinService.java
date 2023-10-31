package com.binnavigator.be.Bin;

import com.binnavigator.be.Bin.Data.BinAddRequest;
import com.binnavigator.be.Bin.Data.BinUpdateRequest;
import com.binnavigator.be.Bin.Data.BinDeleteRequest;
import com.binnavigator.be.Bin.Data.GetResponse;
import com.binnavigator.be.Member.Member;
import com.binnavigator.be.Member.MemberRepository;
import lombok.RequiredArgsConstructor;
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
                .reported(0)
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
            GetResponse getResponse = GetResponse.builder()
                    .binId(bin.getId())
                    .latitude(bin.getLatitude())
                    .longitude(bin.getLongitude())
                    .information(bin.getInformation())
                    .build();
            getResponseList.add(getResponse);
        }
        return getResponseList;
    }

    public List<GetResponse> getByUserId(long userId) {
        List<GetResponse> getResponseList = new ArrayList<>();
        List<Bin> all = binRepository.findAll();
        for(Bin bin : all) {
            if(bin.getOwner().getId() == userId) {
                GetResponse getResponse = GetResponse.builder()
                        .binId(bin.getId())
                        .latitude(bin.getLatitude())
                        .longitude(bin.getLongitude())
                        .information(bin.getInformation())
                        .build();
                getResponseList.add(getResponse);
            }
        }
        return getResponseList;
    }

    public GetResponse getByBinId(long binId) {
        Bin findBin = binRepository.findById(binId).orElseThrow();
        return GetResponse.builder()
                .binId(findBin.getId())
                .latitude(findBin.getLatitude())
                .longitude(findBin.getLongitude())
                .information(findBin.getInformation())
                .build();
    }
}
