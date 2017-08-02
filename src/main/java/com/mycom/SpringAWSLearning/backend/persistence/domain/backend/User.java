package com.mycom.SpringAWSLearning.backend.persistence.domain.backend;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity	
public class User implements Serializable, UserDetails {


 private static final long serialVersionUID = 1L;
 
 @Id
 @GeneratedValue(strategy=GenerationType.AUTO)
 private long id;
 
 private String username;
 
 private String password;
 
 private String email;
 
 private boolean enabled;
 
 @Column(name="first_name")
 private String firstName;
 
 @Column(name="last_name")
 private String lastName;
 
 @Column(name="phone_number")
 private String phoneNumber;
 
 @Length(max=500)
 private String description;
 
 private String country;
 
 @Column(name="profile_image_url")
 private String profileImageUrl;
 
 @Column(name="stripe_customer_id")
 private String stripeCustomerId;
 
 @ManyToOne(fetch=FetchType.EAGER)
 @JoinColumn(name="plan_id")
 private Plan plan;	
 
 @OneToMany(mappedBy="user", cascade=CascadeType.ALL, fetch=FetchType.EAGER)
 private Set<UserRole> userRoles = new HashSet<>();

public Set<UserRole> getUserRoles() {
	return userRoles;
}

public void setUserRoles(Set<UserRole> userRoles) {
	this.userRoles = userRoles;
}

@Override
public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + (int) (id ^ (id >>> 32));
	return result;
}

@Override
public boolean equals(Object obj) {
	if (this == obj)
		return true;
	if (obj == null)
		return false;
	if (getClass() != obj.getClass())
		return false;
	User other = (User) obj;
	if (id != other.id)
		return false;
	return true;
}

public Plan getPlan() {
	return plan;
}

public void setPlan(Plan plan) {
	this.plan = plan;
}

public long getId() {
	return id;
}

public void setId(long id) {
	this.id = id;
}

public String getUsername() {
	return username;
}

public void setUsername(String username) {
	this.username = username;
}

public String getPassword() {
	return password;
}

public void setPassword(String password) {
	this.password = password;
}

public String getEmail() {
	return email;
}

public void setEmail(String email) {
	this.email = email;
}

public boolean isEnabled() {
	return enabled;
}

public void setEnabled(boolean enabled) {
	this.enabled = enabled;
}

public String getFirstName() {
	return firstName;
}

public void setFirstName(String firstName) {
	this.firstName = firstName;
}

public String getLastName() {
	return lastName;
}

public void setLastName(String lastName) {
	this.lastName = lastName;
}

public String getPhoneNumber() {
	return phoneNumber;
}

public void setPhoneNumber(String phoneNumber) {
	this.phoneNumber = phoneNumber;
}

public String getDescription() {
	return description;
}

public void setDescription(String description) {
	this.description = description;
}

public String getCountry() {
	return country;
}

public void setCountry(String country) {
	this.country = country;
}

public String getProfileImageUrl() {
	return profileImageUrl;
}

public void setProfileImageUrl(String profileImageUrl) {
	this.profileImageUrl = profileImageUrl;
}

public String getStripeCustomerId() {
	return stripeCustomerId;
}

public void setStripeCustomerId(String stripeCustomerId) {
	this.stripeCustomerId = stripeCustomerId;
}

@Override
public Collection<? extends GrantedAuthority> getAuthorities() {
	// TODO Auto-generated method stub
	Set<GrantedAuthority> authorities = new HashSet<>();
	userRoles.forEach(ur->authorities.add(new Authority(ur.getRole().getName())));
	return authorities;
}

@Override
public boolean isAccountNonExpired() {
	// TODO Auto-generated method stub
	return true;
}

@Override
public boolean isAccountNonLocked() {
	// TODO Auto-generated method stub
	return true;
}

@Override
public boolean isCredentialsNonExpired() {
	// TODO Auto-generated method stub
	return true;
}
 
 
 
}
