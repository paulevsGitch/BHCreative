<table  align="center">
	<tbody>
		<tr>
			<td width="280px" style="text-align: center;"><img src="https://github.com/paulevsGitch/BHCreative/blob/stapi-2.0/src/main/resources/assets/bhcreative/icon.png"/></td>		
			<td>
				<h2 align="left">BH Creative</h2>
				<a href="https://jitpack.io/#paulevsGitch/BHCreative"><img src="https://jitpack.io/v/paulevsGitch/BHCreative.svg"></a>
				<p>
					Creative mod for Beta Horizons modpack.
				</p>
				<p>
					Dependencies:
					<ul>
						<li><a href="https://github.com/ModificationStation/StationAPI">StationAPI</a></li>
					</ul>
				</p>
			</td>		
		</tr>
	</tbody>
</table>

## Features

- Separate worlds for creative and survival (button in world create option)
- Ability to flight while in creative
- Invulnerability while in creative
- Fast block breaking without tools (no drops)
- Infinity block placing
- Pick block from the world with middle mouse button
- API for mods to add custom tabs
- API for block selection

## How To Use

### Adding dependency

1. **Adding to project**

To add this mod to your gradle project you need several things:
- check is there is jitpack in your build.gradle in repositories section:

```
maven {
	name = 'Jitpack'
	url = 'https://jitpack.io'
}
```

- Add this line to your dependencies section:

```
modImplementation ("com.github.paulevsGitch:BHCreative:${project.bhcreative_version}") {
	transitive false
}
```

- Add version variable to your gradle.properties:

```
bhcreative_version = 0.2.2
```
You can use both version tags (like 0.1.0) and commit names (like d71a19b) as version variable

2. **Adding dependency.**

To add a dependency you need to add "suggests" entry to your fabric.mod.json.
This allows mod to be optional.

```json
"suggests": {
	"bhcreative": "*"
}
```

These methods are **mutually exclusive**, personally I suggest using "optional" variant.

### Adding Listener

To add your own creative tab you need to register listener first. All listeners should be registered in bhcreative:event_bus:

```json
"entrypoints": {
	"bhcreative:event_bus": [
		"yourmodid.listeners.CreativeListener"
	]
}
```

Inside listener you can add even handler and register your tab:

```java
public class CreativeListener {
	public static CreativeTab tab;
	
	@EventListener
	public void onTabInit(TabRegistryEvent event) {
		tab = new SimpleTab(YOUR_MOD_ID.id("yourTabName"), ItemBase.apple); // Making tab
		event.register(tab); // Registering tab
		tab.addItem(new ItemInstance(ItemBase.apple)); // Adding apple
	}
}
```

Now you will have your own tab with apple. You can add any items/blocks that you need with this way.

To add a translation you need to put into your lang file your tab ID and translated name:

```
tab.yourTabName.name=Your Tab Name
```

Translations are located in:

```
src\main\resources\assets\<yourmodid>\stationapi\lang
```
 The main lang file is en_US.lang

### Adding selection items

You can use **BlockSelectAPI** to add custom selection items for blocks
(when player select block from the world with middle mouse button).

You can register Block -> Item converters inside tab listener or in any other place.

Example:

```java
import net.minecraft.block.BlockBase;
import net.minecraft.item.ItemBase;
import paulevs.bhcreative.util.BlockSelectAPI;

public class CreativeListener {
	@EventListener
	public void registerItemConverters(TabRegistryEvent event) {
		// Select apple instead of stone
		BlockSelectAPI(BlockBase.STONE, (state) -> ItemBase.apple);
	}
}
```