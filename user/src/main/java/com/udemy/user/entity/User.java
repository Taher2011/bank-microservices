package com.udemy.user.entity;

import java.io.Serializable;
import javax.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The persistent class for the user database table.
 * 
 */
@Entity
@Table(name = "User")
@Getter
@Setter
@NoArgsConstructor
public class User implements Serializable {

	@Id
	@Column(name = "user_id")
	private String userId;

	@Column(name = "user_pwd")
	private String userPwd;
}