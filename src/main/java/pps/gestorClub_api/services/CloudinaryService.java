package pps.gestorClub_api.services;

import org.springframework.web.multipart.MultipartFile;

public interface CloudinaryService {

    public String uploadImage(MultipartFile file);
}
