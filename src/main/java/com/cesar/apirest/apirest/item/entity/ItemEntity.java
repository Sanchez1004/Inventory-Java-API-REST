    package com.cesar.apirest.apirest.item.entity;

    import jakarta.persistence.Id;
    import jakarta.persistence.Entity;
    import jakarta.persistence.GeneratedValue;
    import jakarta.persistence.GenerationType;
    import lombok.AllArgsConstructor;
    import lombok.NoArgsConstructor;
    import lombok.Builder;
    import lombok.Getter;
    import lombok.Setter;

    @Entity(name = "ITEM")
    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public class ItemEntity {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private String name;
        private String description;
        private double price;


    }
