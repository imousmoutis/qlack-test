package com.eurodyn.qlack.test.cmd.services.mailing;

import com.eurodyn.qlack.fuse.mailing.dto.InternalAttachmentDTO;
import com.eurodyn.qlack.fuse.mailing.dto.InternalMessageDTO;
import com.eurodyn.qlack.fuse.mailing.service.InternalMessageService;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InternalMessageServiceTest {

    private final InternalMessageService internalMessageService;

    @Autowired
    public InternalMessageServiceTest(InternalMessageService internalMessageService) {
        this.internalMessageService = internalMessageService;
    }

    public void sendInternalMail() {
        System.out.println("******************");
        System.out.println("Testing queueEmail method.");

        String internalMessageId = internalMessageService.sendInternalMail(createInternalMessageDTO());
        System.out.println("Internal message with id " + internalMessageId + " has been created.");

        System.out.println("******************");
    }

    private List<InternalAttachmentDTO> createInternalAttachmentsDTO() {
        List<InternalAttachmentDTO> internalAttachments = new ArrayList<>();

        try {
            BufferedImage bImage = ImageIO.read(new File("E:\\pic.jpg"));
            ByteArrayOutputStream bos = new ByteArrayOutputStream();

            ImageIO.write(bImage, "jpg", bos);
            byte[] data = bos.toByteArray();

            InternalAttachmentDTO internalAttachmentDTO = new InternalAttachmentDTO();
            internalAttachmentDTO.setData(data);
            internalAttachmentDTO.setContentType("image/jpeg");
            internalAttachmentDTO.setFilename("pic.jpg");
            internalAttachments.add(internalAttachmentDTO);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return internalAttachments;
    }

    private InternalMessageDTO createInternalMessageDTO() {
        InternalMessageDTO internalMessageDTO = new InternalMessageDTO();
        internalMessageDTO.setSubject("QLACK test internal message");
        internalMessageDTO.setMessage("Internal message content");
        internalMessageDTO.setMailFrom("ioannis.mousmoutis@eurodyn.com");
        internalMessageDTO.setMailTo("wapis_user@delos.eurodyn.com");
        internalMessageDTO.setAttachments(createInternalAttachmentsDTO());

        return internalMessageDTO;
    }

}
