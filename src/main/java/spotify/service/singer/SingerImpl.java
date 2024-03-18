package spotify.service.singer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spotify.exception.customer.CustomerException;
import spotify.mapper.SingerMapper;
import spotify.model.dto.SingerDTO;
import spotify.model.entity.Singer;
import spotify.repository.SingerRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SingerImpl implements ISingerService {
    @Autowired
    private SingerRepository singerRepository;

    @Override
    public String createSinger(SingerDTO singerDTO) throws CustomerException {
        if (singerRepository.existsBySingerName(singerDTO.getSingerName())) {
            throw new CustomerException("Tên ca sĩ đã tồn tại.");
        }
        Singer singer = SingerMapper.INSTANCE.singerDTOToSinger(singerDTO);
        singer.setSingerStatus(true);
        singerRepository.save(singer);
        return "Thêm ca sĩ thành công.";
    }

    @Override
    public String updateSinger(Long id, SingerDTO singerDTO) throws CustomerException {
        List<Singer> list = singerRepository.findSingerBySingerNameAndSingerIdNot(singerDTO.getSingerName(), id);
        if (findById(id) != null) {
            if (!list.isEmpty()) {
                throw new CustomerException("Danh mục đã tồn tại.");
            }
            if (singerRepository.findById(id).get().getSingerStatus()) {
                Singer singer = SingerMapper.INSTANCE.singerDTOToSinger(singerDTO);
                singer.setSingerId(id);
                singer.setSingerStatus(singerRepository.findById(id).get().getSingerStatus());
                singerRepository.save(singer);
                return "Sửa thành công.";
            }
        }
        throw new CustomerException("Không thấy id.");
    }

    @Override
    public SingerDTO findById(Long id) throws CustomerException {
        Optional<Singer> optional = singerRepository.findById(id);
        if (optional.isPresent()) {
            return SingerMapper.INSTANCE.singerToSingerDTO(optional.get());
        }
        throw new CustomerException("Không thấy id");
    }

    @Override
    public List<Singer> getAll() {
        List<Singer> list = new ArrayList<>();
        for (Singer s: singerRepository.findAll()) {
            if (s.getSingerStatus()) {
                list.add(s);
            }
        }
        return list;
    }

    @Override
    public String changStatus(Long id) throws CustomerException {
        Optional<Singer> optional = singerRepository.findById(id);
        if (optional.isPresent()) {
            Singer singer = optional.get();
            singer.setSingerStatus(!optional.get().getSingerStatus());
            singerRepository.save(singer);
            return "Thay đổi trạng thái thành công.";
        }
        throw new CustomerException("Không thấy id.");
    }
}
