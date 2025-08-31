@Entity @Table(name = "compressor_specs")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class CompressorSpecs extends BaseEntity {
    public enum CompressorType { OIL, DRY }
    public enum HeadType { SINGLE, V }

    @OneToOne @JoinColumn(name = "equipment_id", nullable = false, unique = true)
    private Equipment equipment;

    private String brand;

    @Enumerated(EnumType.STRING)
    private CompressorType type;

    private Integer voltage;
    private Double hp;
    private Integer quantity;
    private Integer amps;
    private Integer rpm;
    private Double kw;

    @Enumerated(EnumType.STRING)
    private HeadType headType;
}
