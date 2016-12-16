package tt.wifi.app.hotel.dao;

import org.springframework.stereotype.Repository;
import tt.wifi.app.hotel.model.Hotel;
@Repository
public interface HotelMapper {
    int deleteByPrimaryKey(Long hotelId);

    int insert(Hotel record);

    int insertSelective(Hotel record);

    Hotel selectByPrimaryKey(Long hotelId);

    int updateByPrimaryKeySelective(Hotel record);

    int updateByPrimaryKeyWithBLOBs(Hotel record);

    int updateByPrimaryKey(Hotel record);
}