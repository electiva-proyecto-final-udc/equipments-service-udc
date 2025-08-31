@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChecklistItem {

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ChecklistItemStatus status;

    @Column(length = 500)
    private String notes;
}