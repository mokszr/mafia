package com.smartycoder.mafia.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.MapKeyColumn;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="mafia_job")
public class MafiaJob implements Serializable {
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="mafiajob_seq_gen")
	@SequenceGenerator(
		name="mafiajob_seq_gen",
		sequenceName="mafiajob_seq",
		allocationSize=1
	)	
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	private Long id;

	@NotBlank
    @Column(name="uuid")
	private String uuid;
	
	@Column(name="original_filename")
	private String originalFileName;
	
	@Column(name = "original_file_size")
	private long originalFileSize;
	
	@Column(name = "output_file_size")
	private long outputlFileSize;
	
	@NotBlank
	@Column(name="persisted_filename")
	private String persistedFileName;
	
	@Column(name="output_filename")
	private String outputFileName;
	
	@Enumerated(EnumType.STRING)
	@Column(name="file_type")
	private FileType fileType;
	
	@Column(name = "content_type")
	private String contentType;
	
	@NotBlank
	@Column(name="manipulation_name")
	private String manipulationName;
	
	@ElementCollection
	@JoinTable(name="MANIPULATION_PARAMETERS", joinColumns=@JoinColumn(name="ID"))
	@MapKeyColumn (name="PARAMETER_KEY")
	@Column(name="PARAMETER_VALUE")
	private Map<String, String> manipulationParameters;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name="status")
	private ManipulationStatus status = ManipulationStatus.WAITING;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_on")
	private Date createdOn;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="execution_finished_on")
	private Date executionFinishedOn;

	@Column(name = "duration_in_ms")
	private long durationInMiliseconds;

	@Column(name = "exception_class_name")
	private String exceptionClassName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	public long getOriginalFileSize() {
		return originalFileSize;
	}

	public void setOriginalFileSize(long originalFileSize) {
		this.originalFileSize = originalFileSize;
	}

	public long getOutputlFileSize() {
		return outputlFileSize;
	}

	public void setOutputlFileSize(long outputlFileSize) {
		this.outputlFileSize = outputlFileSize;
	}

	public String getOutputFileName() {
		return outputFileName;
	}

	public void setOutputFileName(String outputFileName) {
		this.outputFileName = outputFileName;
	}

	public String getOriginalFileName() {
		return originalFileName;
	}

	public void setOriginalFileName(String originalFileName) {
		this.originalFileName = originalFileName;
	}

	public String getPersistedFileName() {
		return persistedFileName;
	}

	public void setPersistedFileName(String persistedFileName) {
		this.persistedFileName = persistedFileName;
	}
 
	public FileType getFileType() {
		return fileType;
	}

	public void setFileType(FileType fileType) {
		this.fileType = fileType;
	}

	public String getManipulationName() {
		return manipulationName;
	}

	public void setManipulationName(String manipulationName) {
		this.manipulationName = manipulationName;
	}

	public Map<String, String> getManipulationParameters() {
		return manipulationParameters;
	}

	public void setManipulationParameters(Map<String, String> manipulationParameters) {
		this.manipulationParameters = manipulationParameters;
	}

	public ManipulationStatus getStatus() {
		return status;
	}

	public void setStatus(ManipulationStatus status) {
		this.status = status;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Date getExecutionFinishedOn() {
		return executionFinishedOn;
	}

	public void setExecutionFinishedOn(Date executionFinishedOn) {
		this.executionFinishedOn = executionFinishedOn;
	}

	public void setDurationInMiliseconds(long durationInMiliseconds) {
		this.durationInMiliseconds = durationInMiliseconds;
	}
	
	public long getDurationInMiliseconds() {
		return durationInMiliseconds;
	}

	public void setExceptionClassName(String exceptionClassName) {
		this.exceptionClassName = exceptionClassName;
	}
	
	public String getExceptionClassName() {
		return exceptionClassName;
	}
}
