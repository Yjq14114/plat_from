package tt.wifi.app.hotel.service;

import org.springframework.stereotype.Service;
import tt.wifi.app.hotel.dao.HotelMapper;
import tt.wifi.app.hotel.model.Hotel;
import tt.wifi.app.hotel.service.iface.IHotelService;

import javax.annotation.Resource;

/**
 * Created by yangjq on 2016/9/9.
 */
@Service("hotelService")
public class HotelServiceImpl implements IHotelService{
    @Resource
    private HotelMapper hotelMapper;
    @Override
    public Hotel hotel(long hotelId) {
        return hotelMapper.selectByPrimaryKey(hotelId);
    }
}
