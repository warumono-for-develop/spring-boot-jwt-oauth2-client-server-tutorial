package com.warumono.client.services;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.warumono.client.exceptions.ExceptionReason;
import com.warumono.client.exceptions.RestException;

@Service
public class DeviceService
{
	public void test()
	{
		try
		{
//			Integer.parseInt("one");
//			int i = 100 / 0;
		}
		catch(Exception e)
		{
			throw new RestException(ExceptionReason.SERVER_ERROR, e);
		}
	}
}
