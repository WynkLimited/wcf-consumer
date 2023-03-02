package com.wynk.consumerservice.dao;

import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public class User {
    private String id;
    private String uid;
    private String msisdn;
    private String countryId;
    private String name;
    private String lname;
    private String email;
    private String lang;
    private String gender;                   // m,f
    private long creationDate;
    private long lastActivityDate;
    private boolean isActive = true;
    private String circle;
    private String songQuality = "a";              // l,m,h
    private String avatar;
    private Boolean autoRenewal = null;
    private boolean notifications = true;

    private String iTunesSubscription;

    private List<String> contentLanguages;

    private List<String> onboardingLanguages;

    private Set<String> podcastCategories;

    //    private List<String> userSelectedContentLanguages;
    private String preferredLanguage;

    // For fair use policy
    private int streamedCount;
    private int rentalsCount;

    private LinkedList<Integer> currentOfferIds;

    private String operator;
    private int source;
    private Integer platform;

    private boolean autoPlaylists = true;

    private String downloadQuality = "hd";
    private long lastAutoRenewalOffSettingTimestamp;

    private FUPPack fupPack;
    private String token;
    private String userType;
    private String referrer;
    private int galaxyPurchaseCount;
    private WCFSubscription subscription;
    private UserSubscription userSubscription;

    private Long ndsTS;
    private long expireAt;

    // flag to distinguish users for we set language explicitly
    private boolean isSystemGeneratedContentLang = true;



    private Boolean isDeleted;
}
