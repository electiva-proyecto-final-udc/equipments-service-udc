@Entity
@Table(name = "checklist")
public class Checklist extends BaseEntity {
    @OneToOne @JoinColumn(name = "service_id", nullable = false, unique = true)
    private MaintenanceService service;

    @Enumerated(EnumType.STRING)
    private EquipmentType equipmentType;

    @ElementCollection
    @CollectionTable(name = "checklist_items", joinColumns = @JoinColumn(name = "checklist_id"))
    @MapKeyColumn(name = "item_name")
    private Map<String, ChecklistItem> items;
}