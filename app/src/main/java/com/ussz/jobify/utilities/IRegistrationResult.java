package com.ussz.jobify.utilities;

import com.ussz.jobify.data.Graduate;

public interface IRegistrationResult {

    void saveAccountResult(String result);

    void saveEmailAndPasswordResult(Graduate graduate,String result);
}
