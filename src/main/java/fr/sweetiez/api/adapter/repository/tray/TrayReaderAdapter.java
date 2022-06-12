//package fr.sweetiez.api.adapter.repository.tray;
//
//import fr.sweetiez.api.adapter.shared.TrayMapper;
//import fr.sweetiez.api.core.trays.models.tray.Tray;
//import fr.sweetiez.api.core.trays.models.tray.TrayId;
//import fr.sweetiez.api.core.trays.models.tray.Trays;
//import fr.sweetiez.api.core.trays.models.tray.states.State;
//import fr.sweetiez.api.core.trays.ports.TraysReader;
//import fr.sweetiez.api.infrastructure.repository.trays.TrayRepository;
//
//import java.util.Optional;
//import java.util.UUID;
//import java.util.stream.Collectors;
//
//public class TrayReaderAdapter implements TraysReader {
//
//    private final TrayRepository repository;
//    private final TrayMapper mapper;
//
//    public TrayReaderAdapter(TrayRepository repository, TrayMapper mapper) {
//        this.repository = repository;
//        this.mapper = mapper;
//    }
//
//    public Optional<Tray> findById(TrayId id) {
//        return repository.findById(UUID.fromString(id.value())).map(mapper::toDto);
//    }
//
//    public Trays findAllPublished() {
//        var trays = repository.findAllByState(State.PUBLISHED)
//                .stream()
//                .map(mapper::toDto)
//                .collect(Collectors.toSet());
//
//        return new Trays(trays);
//    }
//
//    public Trays findAll() {
//        var trays =  repository.findAll()
//                .stream()
//                .map(mapper::toDto)
//                .collect(Collectors.toSet());
//
//        return new Trays(trays);
//    }
//}
