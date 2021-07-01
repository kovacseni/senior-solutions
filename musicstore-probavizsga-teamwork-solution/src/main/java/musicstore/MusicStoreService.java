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

@Service
public class MusicStoreService {

    private ModelMapper modelMapper;

    private List<Instrument> instruments = new ArrayList<>();

    private AtomicLong idGenerator = new AtomicLong();


    public MusicStoreService(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public List<InstrumentDTO> getInstruments(Optional<String> brand, Optional<Integer> price) {
        List<Instrument> filtered = instruments
                .stream()
                .filter(i -> brand.isEmpty() || i.getBrand().equals(brand.get()))
                .filter(i-> price.isEmpty() || i.getPrice() == price.get())
                .toList();
        Type targetListType = new TypeToken<List<Instrument>>(){}.getType();
        return modelMapper.map(filtered,targetListType);
    }

    public InstrumentDTO addInstrument(CreateInstrumentCommand command) {
        Instrument instrument =
                new Instrument(idGenerator.incrementAndGet(),
                        command.getBrand(), command.getType(), command.getPrice(), LocalDate.now());
        instruments.add(instrument);
        return modelMapper.map(instrument, InstrumentDTO.class);
    }

    public void deleteAll() {
        idGenerator = new AtomicLong();
        instruments.clear();
    }

    public InstrumentDTO findInstrumentById(Long id) {
        Instrument instrument = findById(id);

        return modelMapper.map(instrument, InstrumentDTO.class);
    }

    private Instrument findById(Long id) {
        return instruments.stream()
                .filter(i -> i.getId().equals(id))
                .findFirst().orElseThrow(() -> new IllegalArgumentException("Cannot find instrument with this id: " + id));
    }

    public InstrumentDTO updatePrice(Long id, UpdatePriceCommand command) {
        Instrument instrument = findById(id);
        if (instrument.getPrice() != command.getPrice()) {
            instrument.setPrice(command.getPrice());
            instrument.setPostDate(LocalDate.now());
        }
        return modelMapper.map(instrument, InstrumentDTO.class);
    }

    public void deleteInstrument(Long id) {
        Instrument instrument = findById(id);
        instruments.remove(instrument);
    }
}
