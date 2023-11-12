package com.binnavigator.be.Bin;

import com.binnavigator.be.Bin.Data.*;
import com.binnavigator.be.Member.Member;
import com.binnavigator.be.Member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.apache.commons.codec.binary.Base64;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class BinService {
    private final BinRepository binRepository;
    private final MemberRepository memberRepository;

    public Long add(BinAddRequest binAddRequest) {
        Member owner = memberRepository.findById(binAddRequest.getUserId()).orElseThrow();
        byte[] imageBytes = Base64.decodeBase64(binAddRequest.getImage());
        UUID imageUuid = UUID.randomUUID();
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(imageBytes);
            BufferedImage bufferedImage = ImageIO.read(bis);
            String userHome = System.getProperty("user.home");
            File outputFile = new File(userHome + File.separator + "images" + File.separator + imageUuid + ".jpg");
            ImageIO.write(bufferedImage, "jpg", outputFile);
        } catch (IOException e) {
            throw new RuntimeException("Error uploading image", e);
        }
        Bin newBin = Bin.builder()
                .owner(owner)
                .information(binAddRequest.getInformation())
                .latitude(binAddRequest.getLatitude())
                .longitude(binAddRequest.getLongitude())
                .image(imageUuid)
                .type(binAddRequest.getType())
                .build();
        return binRepository.save(newBin).getId();
    }

    public boolean delete(BinDeleteRequest binDeleteRequest) {
        Bin deleteBin = binRepository.findById(binDeleteRequest.getBinId()).orElseThrow();
        if(deleteBin.getOwner().getId() == binDeleteRequest.getUserId()) {
            deleteBin.getOwner().deleteBin(deleteBin);
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
        String userHome = System.getProperty("user.home");
        String imagePath = userHome + File.separator + "images" + File.separator + findBin.getImage() + ".jpg";
        String base64Image = getImageAsBase64(imagePath);
        return GetByBinResponse.builder()
                .binId(findBin.getId())
                .latitude(findBin.getLatitude())
                .longitude(findBin.getLongitude())
                .information(findBin.getInformation())
                .image(base64Image)
                .full(findBin.isFull())
                .type(findBin.getType())
                .userId(findBin.getOwner().getId())
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
                .full(bin.isFull())
                .type(bin.getType())
                .userId(bin.getOwner().getId())
                .build();
    }

    public String getImageAsBase64(String imagePath) {
        try{
            byte[] imageBytes = Files.readAllBytes(new File(imagePath).toPath());
            return Base64.encodeBase64String(imageBytes);
        } catch (IOException e) {
            throw new RuntimeException("Cannot find Image");
        }
    }

    @Scheduled(cron = "0 0 6 * * ?", zone = "Asia/Seoul")
    public void setAllIsFullFalse() {
        binRepository.setAllIsFullFalse();
    }
}
