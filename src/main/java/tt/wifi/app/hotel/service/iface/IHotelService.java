package tt.wifi.app.hotel.service.iface;

import org.springframework.stereotype.Repository;
import tt.wifi.app.hotel.model.Hotel;

/**
 * Created by yangjq on 2016/9/9.
 */
@Repository
public interface IHotelService {
    Hotel hotel(long hotelId);
}
