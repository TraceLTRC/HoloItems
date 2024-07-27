package xyz.holocons.mc.holoitemsrevamp.collection;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;

import com.strangeone101.holoitemsapi.item.CustomItem;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import xyz.holocons.mc.holoitemsrevamp.HoloItemsRevamp;
import xyz.holocons.mc.holoitemsrevamp.item.*;

public class CollectionManager {

    private final List<IdolCollection> idolCollections;
    private final Map<String, CustomItem> customItems;

    public CollectionManager(HoloItemsRevamp plugin) {
        this.idolCollections = buildIdolCollections(plugin);

        // Key is the internal name, value is the initialized custom item
        this.customItems = idolCollections.stream()
                .<CustomItem>mapMulti((idolCollection, consumer) -> {
                    for (final var idol : idolCollection.getIdolSet()) {
                        for (final var customItem : idol.getItemSet()) {
                            consumer.accept(customItem);
                        }
                    }
                })
                .collect(Collectors.toMap(CustomItem::getInternalName, Function.identity()));
    }

    /**
     * @return a list of all idol collections
     */
    public List<IdolCollection> getAllCollections() {
        return idolCollections;
    }

    /**
     * @return all custom items that the plugin contains
     */
    public Map<String, CustomItem> getAllItems() {
        return customItems;
    }

    private static List<IdolCollection> buildIdolCollections(HoloItemsRevamp plugin) {
        var en1 = new IdolCollection(
                buildGura(plugin), buildIRyS(plugin), buildCalliope(plugin), buildIna(plugin), buildKiara(plugin),
                buildAmelia(plugin)) {

            @Override
            public @NotNull Material getMaterial() {
                return Material.PAPER;
            }

            @Override
            public Component getDisplayName() {
                return Component.text("EN Generation 1", TextColor.color(0x1D83FF));
            }

            @Override
            public List<Component> getLore() {
                return null;
            }
        };

        var en2 = new IdolCollection(
                buildFauna(plugin), buildBaelz(plugin), buildMumei(plugin), buildKronii(plugin), buildSana(plugin)) {

            @Override
            public @NotNull Material getMaterial() {
                return Material.PAPER;
            }

            @Override
            public Component getDisplayName() {
                return Component.text("EN Generation 2", TextColor.color(0x1D83FF));
            }

            @Override
            public List<Component> getLore() {
                return null;
            }
        };

        var gamers = new IdolCollection(
                buildKorone(plugin), buildOkayu(plugin), buildMio(plugin)) {

            @Override
            public @NotNull Material getMaterial() {
                return Material.PAPER;
            }

            @Override
            public Component getDisplayName() {
                return Component.text("Hololive Gamers", TextColor.color(0x1D83FF));
            }

            @Override
            public List<Component> getLore() {
                return null;
            }
        };

        var gen0 = new IdolCollection(
                buildAZKi(plugin), buildSuisei(plugin), buildRoboco(plugin), buildMiko(plugin), buildSora(plugin)) {

            @Override
            public @NotNull Material getMaterial() {
                return Material.PAPER;
            }

            @Override
            public Component getDisplayName() {
                return Component.text("Hololive Generation 0", TextColor.color(0x1D83FF));
            }

            @Override
            public List<Component> getLore() {
                return null;
            }
        };

        var gen1 = new IdolCollection(
                buildHaato(plugin), buildAki(plugin), buildMatsuri(plugin), buildFubuki(plugin), buildMel(plugin)) {

            @Override
            public @NotNull Material getMaterial() {
                return Material.PAPER;
            }

            @Override
            public Component getDisplayName() {
                return Component.text("Hololive Generation 1", TextColor.color(0x1D83FF));
            }

            @Override
            public List<Component> getLore() {
                return null;
            }
        };

        var gen2 = new IdolCollection(
                buildAqua(plugin), buildShion(plugin), buildAyame(plugin), buildSubaru(plugin), buildChoco(plugin)) {

            @Override
            public @NotNull Material getMaterial() {
                return Material.PAPER;
            }

            @Override
            public Component getDisplayName() {
                return Component.text("Hololive Generation 2", TextColor.color(0x1D83FF));
            }

            @Override
            public List<Component> getLore() {
                return null;
            }
        };

        var gen3 = new IdolCollection(
                buildMarine(plugin), buildFlare(plugin), buildNoel(plugin), buildRushia(plugin), buildPekora(plugin)) {

            @Override
            public @NotNull Material getMaterial() {
                return Material.PAPER;
            }

            @Override
            public Component getDisplayName() {
                return Component.text("Hololive Generation 3", TextColor.color(0x1D83FF));
            }

            @Override
            public List<Component> getLore() {
                return null;
            }
        };

        var gen4 = new IdolCollection(
                buildKanata(plugin), buildLuna(plugin), buildCoco(plugin), buildTowa(plugin), buildWatame(plugin)) {

            @Override
            public @NotNull Material getMaterial() {
                return Material.PAPER;
            }

            @Override
            public Component getDisplayName() {
                return Component.text("Hololive Generation 4", TextColor.color(0x1D83FF));
            }

            @Override
            public List<Component> getLore() {
                return null;
            }
        };

        var gen5 = new IdolCollection(
                buildNene(plugin), buildPolka(plugin), buildBotan(plugin), buildLamy(plugin)) {

            @Override
            public @NotNull Material getMaterial() {
                return Material.PAPER;
            }

            @Override
            public Component getDisplayName() {
                return Component.text("Hololive Generation 5", TextColor.color(0x1D83FF));
            }

            @Override
            public List<Component> getLore() {
                return null;
            }
        };

        var gen6 = new IdolCollection(
                buildKoyori(plugin), buildIroha(plugin), buildLaplus(plugin), buildChloe(plugin), buildLui(plugin)) {

            @Override
            public @NotNull Material getMaterial() {
                return Material.PAPER;
            }

            @Override
            public Component getDisplayName() {
                return Component.text("Hololive Generation 6", TextColor.color(0x1D83FF));
            }

            @Override
            public List<Component> getLore() {
                return null;
            }
        };

        var id1 = new IdolCollection(
                buildIofi(plugin), buildRisu(plugin), buildMoona(plugin)) {

            @Override
            public @NotNull Material getMaterial() {
                return Material.PAPER;
            }

            @Override
            public Component getDisplayName() {
                return Component.text("Hololive ID Generation 1", TextColor.color(0x1D83FF));
            }

            @Override
            public List<Component> getLore() {
                return null;
            }
        };

        var id2 = new IdolCollection(
                buildAnya(plugin), buildOllie(plugin), buildReine(plugin)) {

            @Override
            public @NotNull Material getMaterial() {
                return Material.PAPER;
            }

            @Override
            public Component getDisplayName() {
                return Component.text("Hololive ID Generation 2", TextColor.color(0x1D83FF));
            }

            @Override
            public List<Component> getLore() {
                return null;
            }
        };

        var misc = new IdolCollection(
                buildAchan(plugin)) {

            @Override
            public @NotNull Material getMaterial() {
                return Material.PAPER;
            }

            @Override
            public Component getDisplayName() {
                return Component.text("Hololive Misc", TextColor.color(0x1D83FF));
            }

            @Override
            public List<Component> getLore() {
                return null;
            }
        };

        var stars1 = new IdolCollection(
                buildAruran(plugin), buildMiyabi(plugin), buildIzuru(plugin), buildRikka(plugin)) {

            @Override
            public @NotNull Material getMaterial() {
                return Material.PAPER;
            }

            @Override
            public Component getDisplayName() {
                return Component.text("Holostars Generation 1", TextColor.color(0x1D83FF));
            }

            @Override
            public List<Component> getLore() {
                return null;
            }
        };

        var stars2 = new IdolCollection(
                buildAstel(plugin), buildTemma(plugin), buildRoberu(plugin)) {

            @Override
            public @NotNull Material getMaterial() {
                return Material.PAPER;
            }

            @Override
            public Component getDisplayName() {
                return Component.text("Holostars Generation 2", TextColor.color(0x1D83FF));
            }

            @Override
            public List<Component> getLore() {
                return null;
            }
        };

        var stars3 = new IdolCollection(
                buildOga(plugin), buildShien(plugin)) {

            @Override
            public @NotNull Material getMaterial() {
                return Material.PAPER;
            }

            @Override
            public Component getDisplayName() {
                return Component.text("Holostars Generation 3", TextColor.color(0x1D83FF));
            }

            @Override
            public List<Component> getLore() {
                return null;
            }
        };

        return List.of(
                en1, en2, gamers, gen0, gen1, gen2, gen3, gen4, gen5, gen6, id1, id2, misc, stars1, stars2, stars3);
    }

    private static Idol buildGura(HoloItemsRevamp plugin) {
        return new Idol(new TideRiderItem(plugin)) {

            @Override
            public @NotNull String getSkinUrl() {
                return "http://textures.minecraft.net/texture/33a5d434906bcc5dda02e84e2c09ec728459400b26463ec7baa92b35dba500db";
            }

            @Override
            public Component getDisplayName() {
                return Component.text("Gawr Gura")
                        .color(TextColor.color(0x3969B2))
                        .decoration(TextDecoration.BOLD, true)
                        .decoration(TextDecoration.ITALIC, false);
            }

            @Override
            public List<Component> getLore() {
                return null;
            }
        };
    }

    private static Idol buildIRyS(HoloItemsRevamp plugin) {
        return new Idol() {

            @Override
            public @NotNull String getSkinUrl() {
                return "http://textures.minecraft.net/texture/ed6b315714a6be3e9f35a5caea4445114cbda6110c5e039b4b382d4295870ed2";
            }

            @Override
            public Component getDisplayName() {
                return Component.text("IRyS")
                        .color(TextColor.color(0x361028))
                        .decoration(TextDecoration.BOLD, true)
                        .decoration(TextDecoration.ITALIC, false);
            }

            @Override
            public List<Component> getLore() {
                return null;
            }
        };
    }

    private static Idol buildCalliope(HoloItemsRevamp plugin) {
        return new Idol(new MementoItem(plugin)) {

            @Override
            public @NotNull String getSkinUrl() {
                return "http://textures.minecraft.net/texture/c4b7596eb99e38b917d9b6b6c8e20100408ce8a66f00dd5cca487eb2a909088c";
            }

            @Override
            public Component getDisplayName() {
                return Component.text("Mori Calliope")
                        .color(TextColor.color(0x9F0306))
                        .decoration(TextDecoration.BOLD, true)
                        .decoration(TextDecoration.ITALIC, false);
            }

            @Override
            public List<Component> getLore() {
                return null;
            }
        };
    }

    private static Idol buildIna(HoloItemsRevamp plugin) {
        return new Idol() {

            @Override
            public @NotNull String getSkinUrl() {
                return "http://textures.minecraft.net/texture/3b3068bf7c441280da55f0d96686e39408c4635ec10bfaf594e98e743319e96";
            }

            @Override
            public Component getDisplayName() {
                return Component.text("Ninomae Inanis")
                        .color(TextColor.color(0x3F3F6A))
                        .decoration(TextDecoration.BOLD, true)
                        .decoration(TextDecoration.ITALIC, false);
            }

            @Override
            public List<Component> getLore() {
                return null;
            }
        };
    }

    private static Idol buildKiara(HoloItemsRevamp plugin) {
        return new Idol() {

            @Override
            public @NotNull String getSkinUrl() {
                return "http://textures.minecraft.net/texture/4df77bd1e845d69d6bedc2fe0cbe2a2bb51fc680d51b9ec8d306651420c38300";
            }

            @Override
            public Component getDisplayName() {
                return Component.text("Takanashi Kiara")
                        .color(TextColor.color(0xDC3907))
                        .decoration(TextDecoration.BOLD, true)
                        .decoration(TextDecoration.ITALIC, false);
            }

            @Override
            public List<Component> getLore() {
                return null;
            }
        };
    }

    private static Idol buildAmelia(HoloItemsRevamp plugin) {
        return new Idol() {

            @Override
            public @NotNull String getSkinUrl() {
                return "http://textures.minecraft.net/texture/5454619fe719856a34406d1feccca48085a5e04b371127e205b894dd190964c9";
            }

            @Override
            public Component getDisplayName() {
                return Component.text("Watson Amelia")
                        .color(TextColor.color(0xF2BD37))
                        .decoration(TextDecoration.BOLD, true)
                        .decoration(TextDecoration.ITALIC, false);
            }

            @Override
            public List<Component> getLore() {
                return null;
            }
        };
    }

    private static Idol buildFauna(HoloItemsRevamp plugin) {
        return new Idol() {

            @Override
            public @NotNull String getSkinUrl() {
                return "http://textures.minecraft.net/texture/10cd3e1025c54cf588f796dcd0fa3c5dd84cffb1158d4e31d9c49a9aa07fd030";
            }

            @Override
            public Component getDisplayName() {
                return Component.text("Ceres Fauna")
                        .color(TextColor.color(0x33CA65))
                        .decoration(TextDecoration.BOLD, true)
                        .decoration(TextDecoration.ITALIC, false);
            }

            @Override
            public List<Component> getLore() {
                return null;
            }
        };
    }

    private static Idol buildBaelz(HoloItemsRevamp plugin) {
        return new Idol() {

            @Override
            public @NotNull String getSkinUrl() {
                return "http://textures.minecraft.net/texture/f4a985f1560f57c18383db3cc461502879f9b628c4d3e12ade37be6567323690";
            }

            @Override
            public Component getDisplayName() {
                return Component.text("Hakos Baelz")
                        .color(TextColor.color(0xD3251E))
                        .decoration(TextDecoration.BOLD, true)
                        .decoration(TextDecoration.ITALIC, false);
            }

            @Override
            public List<Component> getLore() {
                return null;
            }
        };
    }

    private static Idol buildMumei(HoloItemsRevamp plugin) {
        return new Idol() {

            @Override
            public @NotNull String getSkinUrl() {
                return "http://textures.minecraft.net/texture/9d76ad235c0391ebbde7259eb9cd0ead776833de272277be477528f0c8bb2f7";
            }

            @Override
            public Component getDisplayName() {
                return Component.text("Nanashi Mumei")
                        .color(TextColor.color(0xC29371))
                        .decoration(TextDecoration.BOLD, true)
                        .decoration(TextDecoration.ITALIC, false);
            }

            @Override
            public List<Component> getLore() {
                return null;
            }
        };
    }

    private static Idol buildKronii(HoloItemsRevamp plugin) {
        return new Idol() {

            @Override
            public @NotNull String getSkinUrl() {
                return "http://textures.minecraft.net/texture/525eabf91608de6d254dd8d0de624fddb99248550dc6b46776e09a6b05a2bacb";
            }

            @Override
            public Component getDisplayName() {
                return Component.text("Ouro Kronii")
                        .color(TextColor.color(0x4B4F67))
                        .decoration(TextDecoration.BOLD, true)
                        .decoration(TextDecoration.ITALIC, false);
            }

            @Override
            public List<Component> getLore() {
                return null;
            }
        };
    }

    private static Idol buildSana(HoloItemsRevamp plugin) {
        return new Idol() {

            @Override
            public @NotNull String getSkinUrl() {
                return "http://textures.minecraft.net/texture/3d1af85313989264d951bebf237ad36723e9260f152d025a8ab5a764807806c8";
            }

            @Override
            public @NotNull Component getDisplayName() {
                return Component.text("Tsukumo Sana")
                        .color(TextColor.color(0xD584AB))
                        .decoration(TextDecoration.BOLD, true)
                        .decoration(TextDecoration.ITALIC, false);
            }

            @Override
            public List<Component> getLore() {
                return null;
            }
        };
    }

    private static Idol buildKorone(HoloItemsRevamp plugin) {
        return new Idol() {

            @Override
            public @NotNull String getSkinUrl() {
                return "http://textures.minecraft.net/texture/e2e7cdd90c12dad9afc00b93a6eb08fcae936c4d9f915e740d45d73022925734";
            }

            @Override
            public Component getDisplayName() {
                return Component.text("Inugami Korone")
                        .color(TextColor.color(0xDBB314))
                        .decoration(TextDecoration.BOLD, true)
                        .decoration(TextDecoration.ITALIC, false);
            }

            @Override
            public List<Component> getLore() {
                return null;
            }
        };
    }

    private static Idol buildOkayu(HoloItemsRevamp plugin) {
        return new Idol() {

            @Override
            public @NotNull String getSkinUrl() {
                return "http://textures.minecraft.net/texture/479dd6d60eb393456587092ac4908f3e56aebcb75de2186836512b68b8f6452d";
            }

            @Override
            public Component getDisplayName() {
                return Component.text("Nekomata Okayu")
                        .color(TextColor.color(0xB37DCF))
                        .decoration(TextDecoration.BOLD, true)
                        .decoration(TextDecoration.ITALIC, false);
            }

            @Override
            public List<Component> getLore() {
                return null;
            }
        };
    }

    private static Idol buildMio(HoloItemsRevamp plugin) {
        return new Idol() {

            @Override
            public @NotNull String getSkinUrl() {
                return "http://textures.minecraft.net/texture/3eda3a5aaf65bcaa20c74618a086130bf10991b802fa90305560b7b0313b65a4";
            }

            @Override
            public Component getDisplayName() {
                return Component.text("Ookami Mio")
                        .color(TextColor.color(0xD91733))
                        .decoration(TextDecoration.BOLD, true)
                        .decoration(TextDecoration.ITALIC, false);
            }

            @Override
            public List<Component> getLore() {
                return null;
            }
        };
    }

    private static Idol buildAZKi(HoloItemsRevamp plugin) {
        return new Idol() {

            @Override
            public @NotNull String getSkinUrl() {
                return "http://textures.minecraft.net/texture/45a654f38302d245e59ec5f9f6cb46748c8342cb552d79653f1198a5faa0a468";
            }

            @Override
            public Component getDisplayName() {
                return Component.text("AZKi")
                        .color(TextColor.color(0xD11C76))
                        .decoration(TextDecoration.BOLD, true)
                        .decoration(TextDecoration.ITALIC, false);
            }

            @Override
            public List<Component> getLore() {
                return null;
            }
        };
    }

    private static Idol buildSuisei(HoloItemsRevamp plugin) {
        return new Idol() {

            @Override
            public @NotNull String getSkinUrl() {
                return "http://textures.minecraft.net/texture/a4eecb3c0b4fc625f59499b0f2c49d4db2d41e45adbf0f79ced3dbdeb07762b3";
            }

            @Override
            public Component getDisplayName() {
                return Component.text("Hoshimachi Suisei")
                        .color(TextColor.color(0x5E8ABB))
                        .decoration(TextDecoration.BOLD, true)
                        .decoration(TextDecoration.ITALIC, false);
            }

            @Override
            public List<Component> getLore() {
                return null;
            }
        };
    }

    private static Idol buildRoboco(HoloItemsRevamp plugin) {
        return new Idol(new MagnetBook(plugin)) {

            @Override
            public @NotNull String getSkinUrl() {
                return "http://textures.minecraft.net/texture/eb29eebc9ebddab2fe6d8775a7b8a94a1d41089bb8176ca5e669e6d61a8067f4";
            }

            @Override
            public Component getDisplayName() {
                return Component.text("Roboco")
                        .color(TextColor.color(0x804F7F))
                        .decoration(TextDecoration.BOLD, true)
                        .decoration(TextDecoration.ITALIC, false);
            }

            @Override
            public List<Component> getLore() {
                return null;
            }
        };
    }

    private static Idol buildMiko(HoloItemsRevamp plugin) {
        return new Idol() {

            @Override
            public @NotNull String getSkinUrl() {
                return "http://textures.minecraft.net/texture/b99dc01dcf28445576f2268882a77706fcb9353ab0c954f96045561a79244c1e";
            }

            @Override
            public Component getDisplayName() {
                return Component.text("Sakura Miko")
                        .color(TextColor.color(0xFF4C74))
                        .decoration(TextDecoration.BOLD, true)
                        .decoration(TextDecoration.ITALIC, false);
            }

            @Override
            public List<Component> getLore() {
                return null;
            }
        };
    }

    private static Idol buildSora(HoloItemsRevamp plugin) {
        return new Idol() {

            @Override
            public @NotNull String getSkinUrl() {
                return "http://textures.minecraft.net/texture/786f6feb285b53e7a85f924dc032d2e5816f5042a4530eecc5c034bee17b1bd0";
            }

            @Override
            public Component getDisplayName() {
                return Component.text("Tokino Sora")
                        .color(TextColor.color(0x0444E7))
                        .decoration(TextDecoration.BOLD, true)
                        .decoration(TextDecoration.ITALIC, false);
            }

            @Override
            public List<Component> getLore() {
                return null;
            }
        };
    }

    private static Idol buildHaato(HoloItemsRevamp plugin) {
        return new Idol() {

            @Override
            public @NotNull String getSkinUrl() {
                return "http://textures.minecraft.net/texture/e763fcc6574940c83b120cc6de16d5c8aee4c4e34c3043a93aee8a7e74ac1ed4";
            }

            @Override
            public Component getDisplayName() {
                return Component.text("Akai Haato")
                        .color(TextColor.color(0xD90629))
                        .decoration(TextDecoration.BOLD, true)
                        .decoration(TextDecoration.ITALIC, false);
            }

            @Override
            public List<Component> getLore() {
                return null;
            }
        };
    }

    private static Idol buildAki(HoloItemsRevamp plugin) {
        return new Idol() {

            @Override
            public @NotNull String getSkinUrl() {
                return "http://textures.minecraft.net/texture/80a144ec031f03b3f3dd4070c78b34cc5a4b1b9ba15bd5eabf5d0280dc25dbf1";
            }

            @Override
            public Component getDisplayName() {
                return Component.text("Aki Rosenthal")
                        .color(TextColor.color(0xD80E89))
                        .decoration(TextDecoration.BOLD, true)
                        .decoration(TextDecoration.ITALIC, false);
            }

            @Override
            public List<Component> getLore() {
                return null;
            }
        };
    }

    private static Idol buildMatsuri(HoloItemsRevamp plugin) {
        return new Idol() {

            @Override
            public @NotNull String getSkinUrl() {
                return "http://textures.minecraft.net/texture/48e7e3b08ba17c89dbc2b85ab5fa5b49916efef149c8035b96c62f215ce9c2c1";
            }

            @Override
            public Component getDisplayName() {
                return Component.text("Natsuiro Matsuri")
                        .color(TextColor.color(0xFF5506))
                        .decoration(TextDecoration.BOLD, true)
                        .decoration(TextDecoration.ITALIC, false);
            }

            @Override
            public List<Component> getLore() {
                return null;
            }
        };
    }

    private static Idol buildFubuki(HoloItemsRevamp plugin) {
        return new Idol(new PlowBook(plugin)) {

            @Override
            public @NotNull String getSkinUrl() {
                return "http://textures.minecraft.net/texture/35c975deb28de3364787908fb0f693b200eb16c50ffd7fcf62cdbe23665b8f39";
            }

            @Override
            public Component getDisplayName() {
                return Component.text("Shirakami Fubuki")
                        .color(TextColor.color(0x6BBFD9))
                        .decoration(TextDecoration.BOLD, true)
                        .decoration(TextDecoration.ITALIC, false);
            }

            @Override
            public List<Component> getLore() {
                return null;
            }
        };
    }

    private static Idol buildMel(HoloItemsRevamp plugin) {
        return new Idol() {

            @Override
            public @NotNull String getSkinUrl() {
                return "http://textures.minecraft.net/texture/9de6c11e48986ae5c41177e2fb317b0e757b5513dc95e8ba1891d3268b5a8fc0";
            }

            @Override
            public Component getDisplayName() {
                return Component.text("Yozora Mel")
                        .color(TextColor.color(0xFEC104))
                        .decoration(TextDecoration.BOLD, true)
                        .decoration(TextDecoration.ITALIC, false);
            }

            @Override
            public List<Component> getLore() {
                return null;
            }
        };
    }

    private static Idol buildAqua(HoloItemsRevamp plugin) {
        return new Idol() {

            @Override
            public @NotNull String getSkinUrl() {
                return "http://textures.minecraft.net/texture/8553f54ee4a8864b7d522d5ca92516ce9e9f9a4ae8bf3fdd2d39bfb74364d828";
            }

            @Override
            public Component getDisplayName() {
                return Component.text("Minato Aqua")
                        .color(TextColor.color(0xFE5DD5))
                        .decoration(TextDecoration.BOLD, true)
                        .decoration(TextDecoration.ITALIC, false);
            }

            @Override
            public List<Component> getLore() {
                return null;
            }
        };
    }

    private static Idol buildShion(HoloItemsRevamp plugin) {
        return new Idol() {

            @Override
            public @NotNull String getSkinUrl() {
                return "http://textures.minecraft.net/texture/b91d1752c25f75ca23fa82afa52710013b0a23f57f6b9f62262be6c25b77028b";
            }

            @Override
            public Component getDisplayName() {
                return Component.text("Murasaki Shion")
                        .color(TextColor.color(0x8A55CB))
                        .decoration(TextDecoration.BOLD, true)
                        .decoration(TextDecoration.ITALIC, false);
            }

            @Override
            public List<Component> getLore() {
                return null;
            }
        };
    }

    private static Idol buildAyame(HoloItemsRevamp plugin) {
        return new Idol() {

            @Override
            public @NotNull String getSkinUrl() {
                return "http://textures.minecraft.net/texture/2a7cbb3aacd6e3d0826478be3b6f425909d7e023b30cf7332f3ca1f995efa868";
            }

            @Override
            public Component getDisplayName() {
                return Component.text("Nakiri Ayame")
                        .color(TextColor.color(0xCA2339))
                        .decoration(TextDecoration.BOLD, true)
                        .decoration(TextDecoration.ITALIC, false);
            }

            @Override
            public List<Component> getLore() {
                return null;
            }
        };
    }

    private static Idol buildSubaru(HoloItemsRevamp plugin) {
        return new Idol() {

            @Override
            public @NotNull String getSkinUrl() {
                return "http://textures.minecraft.net/texture/4a729e45b7b87e9c20ac2c2453eadf74804ea0470fc3cf077be14f5b5569f952";
            }

            @Override
            public Component getDisplayName() {
                return Component.text("Oozora Subaru")
                        .color(TextColor.color(0xBCE719))
                        .decoration(TextDecoration.BOLD, true)
                        .decoration(TextDecoration.ITALIC, false);
            }

            @Override
            public List<Component> getLore() {
                return null;
            }
        };
    }

    private static Idol buildChoco(HoloItemsRevamp plugin) {
        return new Idol() {

            @Override
            public @NotNull String getSkinUrl() {
                return "http://textures.minecraft.net/texture/61d8b4688de24add1ca906ed8a2bea9f3a5ce2416d298e25be072ce03b50a651";
            }

            @Override
            public Component getDisplayName() {
                return Component.text("Yuzuki Choco")
                        .color(TextColor.color(0xDA5884))
                        .decoration(TextDecoration.BOLD, true)
                        .decoration(TextDecoration.ITALIC, false);
            }

            @Override
            public List<Component> getLore() {
                return null;
            }
        };
    }

    private static Idol buildMarine(HoloItemsRevamp plugin) {
        return new Idol() {

            @Override
            public @NotNull String getSkinUrl() {
                return "http://textures.minecraft.net/texture/a452799301568abd5c5e9abcd583176bb5910d1708715109bf6cf52379f57e7";
            }

            @Override
            public Component getDisplayName() {
                return Component.text("Houshou Marine")
                        .color(TextColor.color(0xA42218))
                        .decoration(TextDecoration.BOLD, true)
                        .decoration(TextDecoration.ITALIC, false);
            }

            @Override
            public List<Component> getLore() {
                return null;
            }
        };
    }

    private static Idol buildFlare(HoloItemsRevamp plugin) {
        return new Idol() {

            @Override
            public @NotNull String getSkinUrl() {
                return "http://textures.minecraft.net/texture/cda7ba3f1f1076da479041e407c59c43f4728b1ef4a30c755c20bcfe6add7734";
            }

            @Override
            public Component getDisplayName() {
                return Component.text("Shiranui Flare")
                        .color(TextColor.color(0xFB4E26))
                        .decoration(TextDecoration.BOLD, true)
                        .decoration(TextDecoration.ITALIC, false);
            }

            @Override
            public List<Component> getLore() {
                return null;
            }
        };
    }

    private static Idol buildNoel(HoloItemsRevamp plugin) {
        return new Idol() {

            @Override
            public @NotNull String getSkinUrl() {
                return "http://textures.minecraft.net/texture/b632461445e4bff92039dfcb89c5b17fdc2d73a5d1120b1c9271d8644a4e8c08";
            }

            @Override
            public Component getDisplayName() {
                return Component.text("Shirogane Noel")
                        .color(TextColor.color(0x8A939E))
                        .decoration(TextDecoration.BOLD, true)
                        .decoration(TextDecoration.ITALIC, false);
            }

            @Override
            public List<Component> getLore() {
                return null;
            }
        };
    }

    private static Idol buildRushia(HoloItemsRevamp plugin) {
        return new Idol() {

            @Override
            public @NotNull String getSkinUrl() {
                return "http://textures.minecraft.net/texture/22f76b81f7eafdbd78252ef5dab2b4aa1cafd4acf6e0137a2a147975e7a8ae9c";
            }

            @Override
            public Component getDisplayName() {
                return Component.text("Uruha Rushia")
                        .color(TextColor.color(0x0CE1BB))
                        .decoration(TextDecoration.BOLD, true)
                        .decoration(TextDecoration.ITALIC, false);
            }

            @Override
            public List<Component> getLore() {
                return null;
            }
        };
    }

    private static Idol buildPekora(HoloItemsRevamp plugin) {
        return new Idol() {

            @Override
            public @NotNull String getSkinUrl() {
                return "http://textures.minecraft.net/texture/c8bbdb00195bf569aa5fdb0f61e50bd91235ebb2f36baa4ad66b62f74e56dfa5";
            }

            @Override
            public Component getDisplayName() {
                return Component.text("Usada Pekora")
                        .color(TextColor.color(0x6BB8E7))
                        .decoration(TextDecoration.BOLD, true)
                        .decoration(TextDecoration.ITALIC, false);
            }

            @Override
            public List<Component> getLore() {
                return null;
            }
        };
    }

    private static Idol buildKanata(HoloItemsRevamp plugin) {
        return new Idol() {

            @Override
            public @NotNull String getSkinUrl() {
                return "http://textures.minecraft.net/texture/e7abda0dbc4a1a542c54ae3805101aee15b8082d5d639e2543e8988dcaffe9c";
            }

            @Override
            public Component getDisplayName() {
                return Component.text("Amane Kanata")
                        .color(TextColor.color(0x75C1E7))
                        .decoration(TextDecoration.BOLD, true)
                        .decoration(TextDecoration.ITALIC, false);
            }

            @Override
            public List<Component> getLore() {
                return null;
            }
        };
    }

    private static Idol buildLuna(HoloItemsRevamp plugin) {
        return new Idol() {

            @Override
            public @NotNull String getSkinUrl() {
                return "http://textures.minecraft.net/texture/c2de26831f5b4461357c2eb89b6845d3474b2522827f4066dfea8edc1a824f71";
            }

            @Override
            public Component getDisplayName() {
                return Component.text("Himemori Luna")
                        .color(TextColor.color(0xD86EAD))
                        .decoration(TextDecoration.BOLD, true)
                        .decoration(TextDecoration.ITALIC, false);
            }

            @Override
            public List<Component> getLore() {
                return null;
            }
        };
    }

    private static Idol buildCoco(HoloItemsRevamp plugin) {
        return new Idol() {

            @Override
            public @NotNull String getSkinUrl() {
                return "http://textures.minecraft.net/texture/efe873d27cc5c39880b94dd2dbf45b9c75789a18ea442fbee1fa28ab87ac1981";
            }

            @Override
            public Component getDisplayName() {
                return Component.text("Kiryu Coco")
                        .color(TextColor.color(0xDC7310))
                        .decoration(TextDecoration.BOLD, true)
                        .decoration(TextDecoration.ITALIC, false);
            }

            @Override
            public List<Component> getLore() {
                return null;
            }
        };
    }

    private static Idol buildTowa(HoloItemsRevamp plugin) {
        return new Idol(new SaintQuartzItem(plugin), new HolyFireBlock(plugin)) {

            @Override
            public @NotNull String getSkinUrl() {
                return "http://textures.minecraft.net/texture/df95fc23392af7e5c5c20c76a2208df4922b108f2c89e33522402612c58db508";
            }

            @Override
            public Component getDisplayName() {
                return Component.text("Tokoyami Towa")
                        .color(TextColor.color(0x805A9E))
                        .decoration(TextDecoration.BOLD, true)
                        .decoration(TextDecoration.ITALIC, false);
            }

            @Override
            public List<Component> getLore() {
                return null;
            }
        };
    }

    private static Idol buildWatame(HoloItemsRevamp plugin) {
        return new Idol() {

            @Override
            public @NotNull String getSkinUrl() {
                return "http://textures.minecraft.net/texture/9583baafa2200aadba9fea91e0d8a174ba10e3fc695573c8a50d002f338e486b";
            }

            @Override
            public Component getDisplayName() {
                return Component.text("Tsunomaki Watame")
                        .color(TextColor.color(0xDBDB89))
                        .decoration(TextDecoration.BOLD, true)
                        .decoration(TextDecoration.ITALIC, false);
            }

            @Override
            public List<Component> getLore() {
                return null;
            }
        };
    }

    private static Idol buildNene(HoloItemsRevamp plugin) {
        return new Idol() {

            @Override
            public @NotNull String getSkinUrl() {
                return "http://textures.minecraft.net/texture/a3464e869f6a6a04ea7c4948b98b623345d685ba32338a69db297192ce167724";
            }

            @Override
            public Component getDisplayName() {
                return Component.text("Momosuzu Nene")
                        .color(TextColor.color(0xFA7B10))
                        .decoration(TextDecoration.BOLD, true)
                        .decoration(TextDecoration.ITALIC, false);
            }

            @Override
            public List<Component> getLore() {
                return null;
            }
        };
    }

    private static Idol buildPolka(HoloItemsRevamp plugin) {
        return new Idol() {

            @Override
            public @NotNull String getSkinUrl() {
                return "http://textures.minecraft.net/texture/8d76ec1a4dd059e9e93adecfa0d4dd98bac731b11e23d132a7e889108349ca57";
            }

            @Override
            public Component getDisplayName() {
                return Component.text("Omaru Polka")
                        .color(TextColor.color(0xDCB3A6))
                        .decoration(TextDecoration.BOLD, true)
                        .decoration(TextDecoration.ITALIC, false);
            }

            @Override
            public List<Component> getLore() {
                return null;
            }
        };
    }

    private static Idol buildBotan(HoloItemsRevamp plugin) {
        return new Idol(new BackdashBoots(plugin)) {

            @Override
            public @NotNull String getSkinUrl() {
                return "http://textures.minecraft.net/texture/1601c812f0397c82139ab6baea614e69fd8aa3e563b57437a9fe0879576f6b51";
            }

            @Override
            public Component getDisplayName() {
                return Component.text("Shishiro Botan")
                        .color(TextColor.color(0x9E8788))
                        .decoration(TextDecoration.BOLD, true)
                        .decoration(TextDecoration.ITALIC, false);
            }

            @Override
            public List<Component> getLore() {
                return null;
            }
        };
    }

    private static Idol buildLamy(HoloItemsRevamp plugin) {
        return new Idol() {

            @Override
            public @NotNull String getSkinUrl() {
                return "http://textures.minecraft.net/texture/6a159f3212ebe269e0fd7c0866a27914c91b5fd3f67672aff150fb003540a128";
            }

            @Override
            public Component getDisplayName() {
                return Component.text("Yukihana Lamy")
                        .color(TextColor.color(0x3D99CE))
                        .decoration(TextDecoration.BOLD, true)
                        .decoration(TextDecoration.ITALIC, false);
            }

            @Override
            public List<Component> getLore() {
                return null;
            }
        };
    }

    private static Idol buildKoyori(HoloItemsRevamp plugin) {
        return new Idol() {

            @Override
            public @NotNull String getSkinUrl() {
                return "http://textures.minecraft.net/texture/191053eb510667abca01838471a6f1db300ec0b5551e3bab5b72ab0324dbc25f";
            }

            @Override
            public Component getDisplayName() {
                return Component.text("Hakui Koyori")
                        .color(TextColor.color(0xFDB4CA))
                        .decoration(TextDecoration.BOLD, true)
                        .decoration(TextDecoration.ITALIC, false);
            }

            @Override
            public List<Component> getLore() {
                return null;
            }
        };
    }

    private static Idol buildIroha(HoloItemsRevamp plugin) {
        return new Idol() {

            @Override
            public @NotNull String getSkinUrl() {
                return "http://textures.minecraft.net/texture/5b5e06e0a85b657f1dfe5a57cb7c9bd7fd6ac43c6182e5ada195e7c20b37164d";
            }

            @Override
            public Component getDisplayName() {
                return Component.text("Kazama Iroha")
                        .color(TextColor.color(0xA0D9E8))
                        .decoration(TextDecoration.BOLD, true)
                        .decoration(TextDecoration.ITALIC, false);
            }

            @Override
            public List<Component> getLore() {
                return null;
            }
        };
    }

    private static Idol buildLaplus(HoloItemsRevamp plugin) {
        return new Idol() {

            @Override
            public @NotNull String getSkinUrl() {
                return "http://textures.minecraft.net/texture/4db18056145ed800209dcddaa1f91c54d1904343ac890987483c1d24cb9fe694";
            }

            @Override
            public Component getDisplayName() {
                return Component.text("La+ Darknesss")
                        .color(TextColor.color(0x9A6EC7))
                        .decoration(TextDecoration.BOLD, true)
                        .decoration(TextDecoration.ITALIC, false);
            }

            @Override
            public List<Component> getLore() {
                return null;
            }
        };
    }

    private static Idol buildChloe(HoloItemsRevamp plugin) {
        return new Idol() {

            @Override
            public @NotNull String getSkinUrl() {
                return "http://textures.minecraft.net/texture/8d76ec1a4dd059e9e93adecfa0d4dd98bac731b11e23d132a7e889108349ca57";
            }

            @Override
            public Component getDisplayName() {
                return Component.text("Sakamata Chloe")
                        .color(TextColor.color(0x8A2E30))
                        .decoration(TextDecoration.BOLD, true)
                        .decoration(TextDecoration.ITALIC, false);
            }

            @Override
            public List<Component> getLore() {
                return null;
            }
        };
    }

    private static Idol buildLui(HoloItemsRevamp plugin) {
        return new Idol() {

            @Override
            public @NotNull String getSkinUrl() {
                return "http://textures.minecraft.net/texture/31fac3585ac29059216a03e01234d5189056dd8ab782b1f24e499acc75e0052e";
            }

            @Override
            public Component getDisplayName() {
                return Component.text("Takane Lui")
                        .color(TextColor.color(0xDA9190))
                        .decoration(TextDecoration.BOLD, true)
                        .decoration(TextDecoration.ITALIC, false);
            }

            @Override
            public List<Component> getLore() {
                return null;
            }
        };
    }

    private static Idol buildIofi(HoloItemsRevamp plugin) {
        return new Idol() {

            @Override
            public @NotNull String getSkinUrl() {
                return "http://textures.minecraft.net/texture/43b9d272d6afbe28f04b789c275c2ecf033299ca670c5225155c2085b0ce152e";
            }

            @Override
            public Component getDisplayName() {
                return Component.text("Airani Iofifteen")
                        .color(TextColor.color(0x70D30D))
                        .decoration(TextDecoration.BOLD, true)
                        .decoration(TextDecoration.ITALIC, false);
            }

            @Override
            public List<Component> getLore() {
                return null;
            }
        };
    }

    private static Idol buildRisu(HoloItemsRevamp plugin) {
        return new Idol() {

            @Override
            public @NotNull String getSkinUrl() {
                return "http://textures.minecraft.net/texture/769e7fe812692393554245b1f93a9550ddb4cc5775211bf8e1672257d906ee8c";
            }

            @Override
            public Component getDisplayName() {
                return Component.text("Ayunda Risu")
                        .color(TextColor.color(0xDC7977))
                        .decoration(TextDecoration.BOLD, true)
                        .decoration(TextDecoration.ITALIC, false);
            }

            @Override
            public List<Component> getLore() {
                return null;
            }
        };
    }

    private static Idol buildMoona(HoloItemsRevamp plugin) {
        return new Idol() {

            @Override
            public @NotNull String getSkinUrl() {
                return "http://textures.minecraft.net/texture/c7cc78c3e2b9d73bbb13771c4f5c5ec2eb1f0a332487cc81cb002968c0a63539";
            }

            @Override
            public Component getDisplayName() {
                return Component.text("Moona Hoshinova")
                        .color(TextColor.color(0x7148B3))
                        .decoration(TextDecoration.BOLD, true)
                        .decoration(TextDecoration.ITALIC, false);
            }

            @Override
            public List<Component> getLore() {
                return null;
            }
        };
    }

    private static Idol buildAnya(HoloItemsRevamp plugin) {
        return new Idol() {

            @Override
            public @NotNull String getSkinUrl() {
                return "http://textures.minecraft.net/texture/4547f0eb8f3644e9111a957e1119245d7a4032de20096e038889e2d963ab74f6";
            }

            @Override
            public Component getDisplayName() {
                return Component.text("Anya Melfissa")
                        .color(TextColor.color(0xE89D10))
                        .decoration(TextDecoration.BOLD, true)
                        .decoration(TextDecoration.ITALIC, false);
            }

            @Override
            public List<Component> getLore() {
                return null;
            }
        };
    }

    private static Idol buildOllie(HoloItemsRevamp plugin) {
        return new Idol() {

            @Override
            public @NotNull String getSkinUrl() {
                return "http://textures.minecraft.net/texture/99c1d64470effce955edeb2234e855f869c68bd37ba4562fbde4b9f25d15ac6f";
            }

            @Override
            public Component getDisplayName() {
                return Component.text("Kureiji Ollie")
                        .color(TextColor.color(0xD50D56))
                        .decoration(TextDecoration.BOLD, true)
                        .decoration(TextDecoration.ITALIC, false);
            }

            @Override
            public List<Component> getLore() {
                return null;
            }
        };
    }

    private static Idol buildReine(HoloItemsRevamp plugin) {
        return new Idol() {

            @Override
            public @NotNull String getSkinUrl() {
                return "http://textures.minecraft.net/texture/ddcdd2f067bb9652297d4efebeb09d81218cc3769e25e584a48d265aa2c93482";
            }

            @Override
            public Component getDisplayName() {
                return Component.text("Pavolia Reine")
                        .color(TextColor.color(0x040F7F))
                        .decoration(TextDecoration.BOLD, true)
                        .decoration(TextDecoration.ITALIC, false);
            }

            @Override
            public List<Component> getLore() {
                return null;
            }
        };
    }

    private static Idol buildAchan(HoloItemsRevamp plugin) {
        return new Idol(new DummyBlockBlock(plugin)) {

            @Override
            public @NotNull String getSkinUrl() {
                return "http://textures.minecraft.net/texture/a048d6daeeb808269aff5333724af8cf85656b92360b45353aff72eca2b0c6d6";
            }

            @Override
            public Component getDisplayName() {
                return Component.text("A-chan")
                        .color(TextColor.color(0x534A92))
                        .decoration(TextDecoration.BOLD, true)
                        .decoration(TextDecoration.ITALIC, false);
            }

            @Override
            public List<Component> getLore() {
                return null;
            }
        };
    }

    private static Idol buildAruran(HoloItemsRevamp plugin) {
        return new Idol() {

            @Override
            public @NotNull String getSkinUrl() {
                return "http://textures.minecraft.net/texture/fe1d45c839d18b498bbc21ea1d0fdcdc1cc07ffbd8393b0425f1966bbb9618b3";
            }

            @Override
            public Component getDisplayName() {
                return Component.text("Arurandeisu")
                        .color(TextColor.color(0x3E815F))
                        .decoration(TextDecoration.BOLD, true)
                        .decoration(TextDecoration.ITALIC, false);
            }

            @Override
            public List<Component> getLore() {
                return null;
            }
        };
    }

    private static Idol buildMiyabi(HoloItemsRevamp plugin) {
        return new Idol() {

            @Override
            public @NotNull String getSkinUrl() {
                return "http://textures.minecraft.net/texture/936448472f4b9a48f803458aee1080d9b82f6acc159f312dee1f00f390f2aea7";
            }

            @Override
            public Component getDisplayName() {
                return Component.text("Hanasaki Miyabi")
                        .color(TextColor.color(0xFF4B5A))
                        .decoration(TextDecoration.BOLD, true)
                        .decoration(TextDecoration.ITALIC, false);
            }

            @Override
            public List<Component> getLore() {
                return null;
            }
        };
    }

    private static Idol buildIzuru(HoloItemsRevamp plugin) {
        return new Idol() {

            @Override
            public @NotNull String getSkinUrl() {
                return "http://textures.minecraft.net/texture/f2aca380a83da9ccfa2621f3deddb19c4a1b4095f523756af134c23167d055be";
            }

            @Override
            public Component getDisplayName() {
                return Component.text("Kanade Izuru")
                        .color(TextColor.color(0x314BB6))
                        .decoration(TextDecoration.BOLD, true)
                        .decoration(TextDecoration.ITALIC, false);
            }

            @Override
            public List<Component> getLore() {
                return null;
            }
        };
    }

    private static Idol buildRikka(HoloItemsRevamp plugin) {
        return new Idol() {

            @Override
            public @NotNull String getSkinUrl() {
                return "http://textures.minecraft.net/texture/71aba7e6e60bcc8082cd2511f6d96c82da3184a0bb2b62f17d200d48c96b3e73";
            }

            @Override
            public Component getDisplayName() {
                return Component.text("Rikka")
                        .color(TextColor.color(0xFED2D5))
                        .decoration(TextDecoration.BOLD, true)
                        .decoration(TextDecoration.ITALIC, false);
            }

            @Override
            public List<Component> getLore() {
                return null;
            }
        };
    }

    private static Idol buildAstel(HoloItemsRevamp plugin) {
        return new Idol() {

            @Override
            public @NotNull String getSkinUrl() {
                return "http://textures.minecraft.net/texture/3c274bd7f27e07871460132714636a716dd61982a2751977216a9cbfcdd9abb4";
            }

            @Override
            public Component getDisplayName() {
                return Component.text("Astel Leda")
                        .color(TextColor.color(0x0B55BB))
                        .decoration(TextDecoration.BOLD, true)
                        .decoration(TextDecoration.ITALIC, false);
            }

            @Override
            public List<Component> getLore() {
                return null;
            }
        };
    }

    private static Idol buildTemma(HoloItemsRevamp plugin) {
        return new Idol(new SSKSwordItem(plugin)) {

            @Override
            public @NotNull String getSkinUrl() {
                return "http://textures.minecraft.net/texture/fba52528c1383d032932c741a48ed2a87cbb22c99e1b397d5322455155583c0a";
            }

            @Override
            public Component getDisplayName() {
                return Component.text("Kishido Temma")
                        .color(TextColor.color(0xF5ED94))
                        .decoration(TextDecoration.BOLD, true)
                        .decoration(TextDecoration.ITALIC, false);
            }

            @Override
            public List<Component> getLore() {
                return null;
            }
        };
    }

    private static Idol buildRoberu(HoloItemsRevamp plugin) {
        return new Idol() {

            @Override
            public @NotNull String getSkinUrl() {
                return "http://textures.minecraft.net/texture/52219a99bfb603e2532395195d3746ddee255f212269936bd6ca9724634fe85";
            }

            @Override
            public Component getDisplayName() {
                return Component.text("Yukoku Roberu")
                        .color(TextColor.color(0xE56B00))
                        .decoration(TextDecoration.BOLD, true)
                        .decoration(TextDecoration.ITALIC, false);
            }

            @Override
            public List<Component> getLore() {
                return null;
            }
        };
    }

    private static Idol buildOga(HoloItemsRevamp plugin) {
        return new Idol() {

            @Override
            public @NotNull String getSkinUrl() {
                return "http://textures.minecraft.net/texture/fd3804c19504100cb0eb5231dfc9eccfb1b054e64a5e59d71496e4f2ac0b623";
            }

            @Override
            public Component getDisplayName() {
                return Component.text("Aragami Oga")
                        .color(TextColor.color(0xA4FB41))
                        .decoration(TextDecoration.BOLD, true)
                        .decoration(TextDecoration.ITALIC, false);
            }

            @Override
            public List<Component> getLore() {
                return null;
            }
        };
    }

    private static Idol buildShien(HoloItemsRevamp plugin) {
        return new Idol() {

            @Override
            public @NotNull String getSkinUrl() {
                return "http://textures.minecraft.net/texture/225adcc543adab3af69cd91c9f6587e9c96501349c2cdb01223103c664053f97";
            }

            @Override
            public Component getDisplayName() {
                return Component.text("Kageyama Shien")
                        .color(TextColor.color(0x8B69AE))
                        .decoration(TextDecoration.BOLD, true)
                        .decoration(TextDecoration.ITALIC, false);
            }

            @Override
            public List<Component> getLore() {
                return null;
            }
        };
    }
}
