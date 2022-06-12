//package fr.sweetiez.api.adapter.repository.tray;
//
//import fr.sweetiez.api.adapter.shared.TrayMapper;
//import fr.sweetiez.api.core.trays.models.tray.Tray;
//import fr.sweetiez.api.core.trays.ports.TraysWriter;
//import fr.sweetiez.api.infrastructure.repository.trays.TrayRepository;
//
//public class TrayWriterAdapter implements TraysWriter {
//
//    private final TrayRepository repository;
//    private final TrayMapper mapper;
//
//    public TrayWriterAdapter(TrayRepository repository, TrayMapper mapper) {
//        this.repository = repository;
//        this.mapper = mapper;
//    }
//
//    public Tray save(Tray tray) {
//        var entity = repository.save(mapper.toEntity(tray));
//        return mapper.toDto(entity);
//    }
//}
