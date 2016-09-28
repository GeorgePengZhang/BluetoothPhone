package com.aura.bluetoothphone.bean;

import java.io.Serializable;
import java.util.Locale;

import android.database.Cursor;

import com.aura.bluetoothphone.utils.connects.CnToCharParser;
import com.aura.bluetoothphone.utils.connects.ContactsUtils;


/**
 * @ClassName: ContactsPhoneBean
 * @Description: TODO
 * @author: steven zhang
 * @date: Sep 27, 2016 2:31:39 PM
 */
public class ContactsPhoneBean implements Comparable<ContactsPhoneBean>,Serializable {
	
	private int phoneId;
	private int contactId;
	private long photoId;
	private int type;
	private String name;
	private String number;
	private String label;
	private String lookup;
	private String sortkey;
	private String phone;//真正的号码没有显示格式，纯数字
	private String firstLetter;
	private String spellname;
	
	public ContactsPhoneBean() {
	}
	
	public ContactsPhoneBean(int phoneId, int contactId, long photoId, int type, String name, 
			String number, String label, String lookup, String sortkey, String phone, String firstLetter, String spellname) {
		this.phoneId = phoneId;
		this.contactId = contactId;
		this.photoId = photoId;
		this.type = type;
		this.name = name;
		this.number = number;
		this.label = label;
		this.lookup = lookup;
		this.sortkey = sortkey;
		this.phone = phone;
		this.firstLetter = firstLetter;
		this.spellname = spellname;
	}
	
	public int getPhoneId() {
		return phoneId;
	}
	
	public void setPhoneId(int phoneId) {
		this.phoneId = phoneId;
	}
	
	public int getContactId() {
		return contactId;
	}
	
	public void setContactId(int contactId) {
		this.contactId = contactId;
	}
	
	public long getPhotoId() {
		return photoId;
	}
	
	public void setPhotoId(long photoId) {
		this.photoId = photoId;
	}
	
	public int getType() {
		return type;
	}
	
	public void setType(int type) {
		this.type = type;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getNumber() {
		return number;
	}
	
	public void setNumber(String number) {
		this.number = number;
	}
	
	public String getLabel() {
		return label;
	}
	
	public void setLabel(String label) {
		this.label = label;
	}
	
	public String getLookup() {
		return lookup;
	}
	
	public void setLookup(String lookup) {
		this.lookup = lookup;
	}
	
	public String getSortkey() {
		return sortkey;
	}
	
	public void setSortkey(String sortkey) {
		this.sortkey = sortkey;
	}
	
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getFirstLetter() {
		return firstLetter;
	}

	public void setFirstLetter(String firstLetter) {
		this.firstLetter = firstLetter;
	}
	
	public String getSpellname() {
		return spellname;
	}

	public void setSpellname(String spellname) {
		this.spellname = spellname;
	}

	@Override
	public int compareTo(ContactsPhoneBean another) {
		if (!"#".equals(this.getFirstLetter()) && "#".equals(another.getFirstLetter())) {
			return -1;
		} else if ("#".equals(this.getFirstLetter()) && !"#".equals(another.getFirstLetter())) {
			return 1; 
		} else {
			return this.getSpellname().compareTo(another.getSpellname());
		}
	}
	
	public ContactsPhoneBean clone(){
		ContactsPhoneBean bean = new ContactsPhoneBean(phoneId, contactId, photoId, type, name, number, 
				label, lookup, sortkey, phone, firstLetter, spellname);
		return bean;
	}

	@Override
	public boolean equals(Object o) {
		ContactsPhoneBean bean = (ContactsPhoneBean)o;
		return this.phone.equals(bean.phone) && this.name.equals(bean.name);
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("phoneId:").append(phoneId).append(",")
		.append("contactId:").append(contactId).append(",")
		.append("photoId:").append(photoId).append(",")
		.append("type:").append(type).append(",")
		.append("name:").append(name).append(",")
		.append("number:").append(number).append(",")
		.append("label:").append(label).append(",")
		.append("lookup:").append(lookup).append(",")
		.append("sortkey:").append(sortkey).append(",")
		.append("phone:").append(phone).append(",")
		.append("firstLetter:").append(firstLetter).append(",")
		.append("spellname:").append(spellname).append(",");
		return builder.toString();
	}
	
	/**
	 * 将数据库中的电话数据转化为bean对象
	 * @param cursor
	 * @return
	 */
	public static ContactsPhoneBean getBeanFromCursor(Cursor cursor) {
		ContactsPhoneBean bean = null;
		if (cursor != null) {
			
			int phoneid = cursor.getInt(ContactsUtils.Phone.PHONE_ID);
			int contactid = cursor.getInt(ContactsUtils.Phone.PHONE_CONTACT_ID);
			long photoid = cursor.getLong(ContactsUtils.Phone.PHONE_PHOTO_ID);
			int type = cursor.getInt(ContactsUtils.Phone.PHONE_TYPE);
			String name = cursor.getString(ContactsUtils.Phone.PHONE_DISPLAY_NAME);
			String label = cursor.getString(ContactsUtils.Phone.PHONE_LABEL);
			String number = cursor.getString(ContactsUtils.Phone.PHONE_NUMBER);
			String lookup = cursor.getString(ContactsUtils.Phone.PHONE_LOOKUP_KEY);
			String sortkey = cursor.getString(ContactsUtils.Phone.PHONE_SORT_KEY);
			
			//讲电话号码中所有非数字的部分去掉，只留下数字部分
			String phone = number.replaceAll("\\D", "");
			
			//汉字转拼音
			String spellname = CnToCharParser.getInstance().getSpell(name, false);
			String firstLetter = getFirstLetter(spellname);
			
			bean = new ContactsPhoneBean(phoneid, contactid, photoid, type, name, number, label, lookup, sortkey, phone, firstLetter, spellname);
		}
		
		return bean;
	}

	/**
	 * 获取首字母 第一个字符非字母的都设置为#
	 * @param name
	 * @return
	 */
	private static String getFirstLetter(String name) {
		String firstLetter = name.substring(0, 1).toUpperCase(Locale.getDefault());
		if (!firstLetter.matches("[A-Z]")) {
			firstLetter = "#";
		}
		return firstLetter;
	}
}
