package ru.hogwarts.school.dto;

import java.util.Objects;

public class AvatarDTO {
    private Long id;
    private long fileSize;
    private String mediaType;
    private long studentId;

    public AvatarDTO() {
    }

    public AvatarDTO(Long id, long fileSize, String mediaType, long studentId) {
        this.id = id;
        this.fileSize = fileSize;
        this.mediaType = mediaType;
        this.studentId = studentId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public long getStudentId() {
        return studentId;
    }

    public void setStudentId(long studentId) {
        this.studentId = studentId;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        AvatarDTO avatarDTO = (AvatarDTO) object;
        return fileSize == avatarDTO.fileSize && studentId == avatarDTO.studentId && Objects.equals(id, avatarDTO.id) && Objects.equals(mediaType, avatarDTO.mediaType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fileSize, mediaType, studentId);
    }

    @Override
    public String toString() {
        return "AvatarDTO{" +
                "id=" + id +
                ", fileSize=" + fileSize +
                ", mediaType='" + mediaType + '\'' +
                ", studentId=" + studentId +
                '}';
    }
}
