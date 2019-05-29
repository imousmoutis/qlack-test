package com.eurodyn.qlack.test.cmd.services.mailing;

import com.eurodyn.qlack.fuse.mailing.dto.AttachmentDTO;
import com.eurodyn.qlack.fuse.mailing.dto.EmailDTO;
import com.eurodyn.qlack.fuse.mailing.service.MailService;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.imageio.ImageIO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MailServiceTest {

    private MailService mailService;

    @Autowired
    public MailServiceTest(MailService mailService) {
        this.mailService = mailService;
    }

    public void queueEmail() {
        System.out.println("******************");
        System.out.println("Testing queueEmail method.");

        String emailId = mailService.queueEmail(createEmailDTO());
        System.out.println("Email with id " + emailId + " has been queued.");
        sendOne(emailId);

        System.out.println("******************");
    }

    private void sendOne(String emailId) {
        System.out.println("******************");
        System.out.println("Testing sendOne method.");

        mailService.sendOne(emailId);
        System.out.println("Mail with id " + emailId + " has been sent.");

        System.out.println("******************");
    }

    private List<AttachmentDTO> createAttachmentsDTO() {
        List<AttachmentDTO> attachments = new ArrayList<>();

        try {
            BufferedImage bImage = ImageIO.read(new File("E:\\pic.jpg"));
            ByteArrayOutputStream bos = new ByteArrayOutputStream();

            ImageIO.write(bImage, "jpg", bos);
            byte[] data = bos.toByteArray();

            AttachmentDTO attachmentDTO = new AttachmentDTO();
            attachmentDTO.setData(data);
            attachmentDTO.setContentType("image/jpeg");
            attachmentDTO.setFilename("pic.jpg");
            attachments.add(attachmentDTO);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return attachments;
    }

    private EmailDTO createEmailDTO() {
        EmailDTO emailDTO = new EmailDTO();
        emailDTO.setSubject("QLACK test email");
        emailDTO.setBody("<p> test</p>");
        emailDTO.setFromEmail("ioannis.mousmoutis@eurodyn.com");
        emailDTO.setToEmails(Arrays.asList("wapis_user@delos.eurodyn.com"));
        emailDTO.setEmailType(EmailDTO.EMAIL_TYPE.HTML);
        emailDTO.setAttachments(createAttachmentsDTO());
        return emailDTO;
    }
}
