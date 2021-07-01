package musicstore;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class MusicStoreService {

    private List<Instrument> instruments = new ArrayList<>();
    private AtomicLong idGenerator = new AtomicLong();
    private ModelMapper modelMapper;

    public MusicStoreService(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public List<InstrumentDTO> getInstruments(Optional<String> brand, Optional<Integer> price) {
        Type targetListType = new TypeToken<List<InstrumentDTO>>() {
        }.getType();
        List<Instrument> result = instruments.stream()
                .filter(instrument -> brand.isEmpty() || instrument.getBrand().equals(brand.get()))
                .filter(instrument -> price.isEmpty() || instrument.getPrice() == price.get().intValue())
                .collect(Collectors.toList());

        return modelMapper.map(result, targetListType);
    }

    public InstrumentDTO createInstrument(CreateInstrumentCommand command) {
        Instrument instrument = new Instrument(idGenerator.incrementAndGet(), command.getBrand(),
                command.getType(), command.getPrice(), LocalDate.now());
        instruments.add(instrument);
        return modelMapper.map(instrument, InstrumentDTO.class);
    }

    public void deleteAllInstruments() {
        instruments.clear();
        idGenerator = new AtomicLong();
    }

    public InstrumentDTO findById(long id) {
        Instrument result = instruments.stream()
                .filter(instrument -> instrument.getId().longValue() == id)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Instrument with id = " + id + " not found."));
        return modelMapper.map(result, InstrumentDTO.class);
    }

    public InstrumentDTO updatePrice(long id, int newPrice) {
        Instrument result = instruments.stream()
                .filter(instrument -> instrument.getId().longValue() == id)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Instrument with id = " + id + " not found."));
        if (result.getPrice() != newPrice) {
            result.setPrice(newPrice);
            result.setPostDate(LocalDate.now());
        }
        return modelMapper.map(result, InstrumentDTO.class);
    }

    public void deleteInstrument(long id) {
        Instrument result = instruments.stream()
                .filter(instrument -> instrument.getId().longValue() == id)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Instrument with id = " + id + " not found."));
        instruments.remove(result);
    }
}
