package com.mx3.knyghtryder;

import com.smartdevicelink.proxy.rpc.GetVehicleDataResponse;

/**
 * Created by David on 2/26/2016.
 */
public interface  VehicleInfoDisplay
{
    void updateVehicleInfo(GetVehicleDataResponse info);
}
