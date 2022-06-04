<table>
	<tbody>
		<tr>
			<td width="210px"><img src="https://github.com/paulevsGitch/BHCreative/blob/main/src/main/resources/assets/bhcreative/icon.png"/></td>		
			<td>
				<h2 align="left">BHCreative</h2>
				<a href="https://jitpack.io/#paulevsGitch/BHCreative"><img src="https://jitpack.io/v/paulevsGitch/BHCreative.svg"></a>
				<p>
					Creative mod for Beta Horizons modpack.
				</p>
				<p>
					Dependencies:
					<ul>
						<li><a href="https://github.com/ModificationStation/StationAPI">Station API</a></li>
					</ul>
				</p>
			</td>		
		</tr>
	</tbody>
</table>

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
modImplementation "com.github.paulevsGitch:BHCreative:${project.bhcreative_version}"
```

- Add version variable to your gradle.properties:

```
bhcreative_version = d71a19b
```
You can use both version tags (like 0.1.0) and commit names (like d71a19b) as version variable

2. **Adding dependency.**

To add a dependency you need to add "suggests" entry to your fabric.mod.json.

```json
"suggests": {
	"bhcreative": "*"
}
```

This allows mod to be optional dependency. If you need to add creative as a required dependency you can add same entry as for other mods:

```json
"depends": {
	"minecraft": "1.0.0-beta.7.3",
	"bhcore": "*",
	"stationapi": "*",
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
		// ItemConvertible here is a tab icon. If you want custom icons
		// you can extend tab class and implement your own icon rendering.
		tab = new SimpleTab(new Identifier(yourModID, "yourTabName"), (ItemConvertible) ItemBase.apple);
		event.register(tab); // Registering tab
		CreativeListener.tab.addItem(new ItemInstance(ItemBase.apple)); // Adding apple
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