package com.wynk.consumerservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.reflect.TypeToken;
import com.wynk.consumerservice.utils.AppUtils;
import lombok.Builder;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Document(collection = "users")
@Builder
@Data
public class User extends BaseEntity{

    @Field(MongoUserEntityKey.uid)
    @JsonProperty(MongoUserEntityKey.uid)
    public String uid;

    @Field(MongoUserEntityKey.msisdn)
    @JsonProperty(MongoUserEntityKey.msisdn)
    private String msisdn;

    @Field(MongoUserEntityKey.name)
    @JsonProperty(MongoUserEntityKey.name)
    private String name;

    @Field(MongoUserEntityKey.lname)
    @JsonProperty(MongoUserEntityKey.lname)
    private String lname;

    @Field(MongoUserEntityKey.email)
    @JsonProperty(MongoUserEntityKey.email)
    private String email;

    @Field(MongoUserEntityKey.lang)
    @JsonProperty(MongoUserEntityKey.lang)
    private String lang;

    @Field(MongoUserEntityKey.gender)
    @JsonProperty(MongoUserEntityKey.gender)
    private String gender;

    @Field(MongoUserEntityKey.creationDate)
    @JsonProperty(MongoUserEntityKey.creationDate)
    private Long creationDate;

    @Field(MongoUserEntityKey.expireAt)
    @JsonProperty(MongoUserEntityKey.expireAt)
    private Date expireAt;

    @Field(MongoUserEntityKey.lastActivityDate)
    @JsonProperty(MongoUserEntityKey.lastActivityDate)
    private Long lastActivityDate;

    @Field(MongoUserEntityKey.circle)
    @JsonProperty(MongoUserEntityKey.circle)
    private String circle;

    @Field(MongoUserEntityKey.songQuality)
    @JsonProperty(MongoUserEntityKey.songQuality)
    private String songQuality = "a";

    @Field(MongoUserEntityKey.avatar)
    @JsonProperty(MongoUserEntityKey.avatar)
    private String avatar;

    @Field(MongoUserEntityKey.notifications)
    @JsonProperty(MongoUserEntityKey.notifications)
    private Boolean notifications = true;

    @Field(MongoUserEntityKey.WynkBasicKeys.basicHasManuallySelectedLang)
    @JsonProperty(MongoUserEntityKey.WynkBasicKeys.basicHasManuallySelectedLang)
    private boolean basicHasManuallySelectedLangauge = false;

    @Field(MongoUserEntityKey.fbToken)
    @JsonProperty(MongoUserEntityKey.fbToken)
    private String fbToken;

    @Field(MongoUserEntityKey.iTunesSubscription)
    @JsonProperty(MongoUserEntityKey.iTunesSubscription)
    private String iTunesSubscription;

    @Field(MongoUserEntityKey.contentLanguages)
    @JsonProperty(MongoUserEntityKey.contentLanguages)
    private Object contentLangStr;

    @JsonIgnore
    private List<String> contentLanguages;

    @Field(MongoUserEntityKey.onboardingLanguages)
    @JsonProperty(MongoUserEntityKey.onboardingLanguages)
    private List<String> onboardingLanguages;

    @Field(MongoUserEntityKey.devices)
    @JsonProperty(MongoUserEntityKey.devices)
    private List<UserDevice> devices;

    @Field(MongoUserEntityKey.operator)
    @JsonProperty(MongoUserEntityKey.operator)
    private String operator;

    @Field(MongoUserEntityKey.platform)
    @JsonProperty(MongoUserEntityKey.platform)
    private Integer platform;

    @Field(MongoUserEntityKey.downloadQuality)
    @JsonProperty(MongoUserEntityKey.downloadQuality)
    private String downloadQuality = "h";

    @Field(MongoUserEntityKey.token)
    @JsonProperty(MongoUserEntityKey.token)
    private String token;

    @Field(MongoUserEntityKey.isSystemGeneratedContentLang)
    @JsonProperty(MongoUserEntityKey.isSystemGeneratedContentLang)
    private Boolean isSystemGeneratedContentLang;

    @Field(MongoUserEntityKey.source)
    @JsonProperty(MongoUserEntityKey.source)
    private Integer source;

    @Field(MongoUserEntityKey.userType)
    @JsonProperty(MongoUserEntityKey.userType)
    private String userType;

    @Field(MongoUserEntityKey.packs)
    @JsonProperty(MongoUserEntityKey.packs)
    private HashMap<String, FUPPack> packs;

    /** code for old WCF Subscription object */
    @Field(MongoUserEntityKey.subscription)
    @JsonProperty(MongoUserEntityKey.subscription)
    private WCFSubscription subscription;

    public WCFSubscription getSubscription() {
        return subscription;
    }

    /** code for new WCF Subscription object */
    @Field(MongoUserEntityKey.newSubscription)
    @JsonProperty(MongoUserEntityKey.newSubscription)
    private NewWCFSubscription newSubscription;

    public NewWCFSubscription getNewSubscription() {
        return newSubscription;
    }

    @Field(MongoUserEntityKey.WynkBasicKeys.basicContentLanguages)
    @JsonProperty(MongoUserEntityKey.WynkBasicKeys.basicContentLanguages)
    private List<String> basicContentLanguage;

    @Field(MongoUserEntityKey.WynkBasicKeys.basicSelectedArtist)
    @JsonProperty(MongoUserEntityKey.WynkBasicKeys.basicSelectedArtist)
    private List<String> basicSelectedArtist;

    @Field(MongoUserEntityKey.WynkBasicKeys.basicSelectedPlaylist)
    @JsonProperty(MongoUserEntityKey.WynkBasicKeys.basicSelectedPlaylist)
    private List<String> basicSelectedPlaylist;

    @Field(MongoUserEntityKey.vasDND)
    @JsonProperty(MongoUserEntityKey.vasDND)
    private String vasDND;


    @Field(MongoUserEntityKey.podcastCategories)
    @JsonProperty(MongoUserEntityKey.podcastCategories)
    private Set<String> podcastCategories;

    @Field(MongoUserEntityKey.countryId)
    @JsonProperty(MongoUserEntityKey.countryId)
    private String countryId;


    public List<String> getContentLanguages() {
        try {
            String contentLang = String.valueOf(contentLangStr);
            if (StringUtils.isBlank(contentLang)) {
                return new ArrayList<>();
            }
            return AppUtils.gson.fromJson(contentLang, new TypeToken<ArrayList<String>>(){}.getType());
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }


    public interface MongoUserEntityKey {
        String id = "id";

        String uid = "uid";

        String msisdn = "msisdn";

        String name = "na";

        String lname = "ln";

        String email = "em";

        String lang = "lg";

        String gender = "gn";

        String creationDate = "cd";

        String lastActivityDate = "lad";

        String circle = "cl";

        String songQuality = "sq";

        String avatar = "av";

        String notifications = "notif";

        String fbToken = "fbt";

        String iTunesSubscription = "its";

        String contentLanguages = "clang";

        String onboardingLanguages = "oblang";

        String devices = "ud";

        String operator = "op";

        String platform = "plt";

        String downloadQuality = "dq";

        String packs = "packs";

        String token = "tkn";

        String userType = "ut";

        String subscription = "wcfs";

        String newSubscription = "subs";

        String vasDND = "vasdnd";

        String source = "src";
        String autoRenewal = "ar";
        String lastAutoRenewalOffSettingTimestamp = "laost";
        String isSystemGeneratedContentLang = "sgcl";

        String expireAt = "eat";

        String autoPlaylists = "apl";

        String podcastCategories = "podcast_categories";

        String countryId = "cid";





        interface WynkBasicKeys {
            /** selected artist at time of wynk basic onboarding */
            String basicSelectedArtist = "basic_osa";

            /** selected playlists at time of wynk basic onboarding */
            String basicSelectedPlaylist = "basic_osp";

            String basicHasManuallySelectedLang = "basic_hmsl";

            String basicContentLanguages = "basic_clang";
        }
    }
}
