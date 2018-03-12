package com.warumono.client.enums;

import java.util.Arrays;
import java.util.Collection;

import com.google.common.collect.Sets;
import com.warumono.client.enums.converters.AbstractEnumeratable;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum AcceptanceType implements AbstractEnumeratable<AcceptanceType>
{
	TERMS_OF_POLICY("TER"), // 이용약관
	PRIVACY_OF_POLICY("PRI"), // 개인정보취급방침
	AGE_VERIFICATION("AGE"), // 14세 이상 확인
	SMS("SMS"), // 단문메시지 서비스
	EMAIL("EMA"), // 이메일 수신동의
	NOTIFICATION("NOF"), // 푸쉬 알림
	;
	
	public static final Collection<AcceptanceType> TERMS = Sets.newHashSet(TERMS_OF_POLICY, PRIVACY_OF_POLICY, AGE_VERIFICATION);
	public static final Collection<AcceptanceType> PREFERENCES = Sets.newHashSet(SMS, EMAIL, NOTIFICATION);
	
	private String dbData;

	@Override
	public String getToDatabaseColumn(AcceptanceType attribute)
	{
		return dbData;
	}

	@Override
	public AcceptanceType getToEntityAttribute(String dbData)
	{
		return Arrays.stream(AcceptanceType.values()).filter(e -> e.getDbData().equals(dbData)).findFirst().orElseThrow(null);
	}
}
