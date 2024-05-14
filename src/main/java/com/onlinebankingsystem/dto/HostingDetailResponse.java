package com.onlinebankingsystem.dto;

import java.util.ArrayList;
import java.util.List;

import com.onlinebankingsystem.entity.HostingDetail;

import org.springframework.core.io.Resource;

public class HostingDetailResponse extends CommonApiResponse {
    private HostingDetail hostingDetail = new HostingDetail();
    private Resource imageBytes;

    public HostingDetail getHostingDetail() {
        return hostingDetail;
    }

    public void setHostingDetail(HostingDetail hostingDetail) {
        this.hostingDetail = hostingDetail;
    }

    public Resource getImageBytes() {
        return imageBytes;
    }

    public void setImageBytes(Resource imageBytes) {
        this.imageBytes = imageBytes;
    }

}
