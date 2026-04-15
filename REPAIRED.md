# Create Repair's Big List of Backports
This is a list of bug fixes, crash fixes, recipes, visuals, tweaks, optimizations, and dupe fixes that Create Repair has backported.  
Fixes marked with [FM] require either Mixinbooster (recommended) or Sinytra Connector to be (fully) fixed.

## Optimizations
- [x] [Significantly optimize Funnel extraction and MountedItemStorageWrapper](https://github.com/Creators-of-Create/Create/issues/9706) (Thanks, PoppyBlossom!)

## Visual Fixes
- [x] [Correct the spelling of "Assember"](https://github.com/Creators-of-Create/Create/pull/9652) (Thanks, PoppyBlossom!)
- [x] [Correct the spelling of "Restone" and "Amethust"](https://github.com/Creators-of-Create/Create/pull/9658) (Thanks, PoppyBlossom!)
- [x] [FM] [Fix Backtanks not having the enchantment glint when worn](https://github.com/Creators-of-Create/Create/commit/79b5d3b37e2d1970818dd97ca460b649cd0a456c) (Thanks, IThundxr!)
- [x] [Fix emissive textures in copycats](https://github.com/Creators-of-Create/Create/issues/9675) (Thanks, IThundxr!)
- [x] [Fix the Encased Fan shaft's texture being rotated incorrectly](https://github.com/Creators-of-Create/Create/commit/ecbb4f20db4cd5224416b6415d384363af288543) (Thanks, IThundxr!)
- [x] [Fix upright item renderers using per-tick position instead of camera position](https://github.com/Creators-of-Create/Create/pull/9942)
- [x] [Prevent ladders from being split into multiple meshes when assembled in contraptions](https://github.com/Creators-of-Create/Create/commit/25c625dfb8ff8e3fb9cad5ff5deb39ebc1fa202a) (Thanks, Jozufozu!)
- [x] [Fix hand crank rotation visuals](https://github.com/Creators-of-Create/Create/pull/8828) (Thanks, nopeless!)

## Recipe Tweaks
- [x] [Adjust the Clay crushing recipe to always give 4 clay balls](https://github.com/Creators-of-Create/Create/commit/2533d6933eed770f6d91de53c8f67b8a9633d24c) (Thanks, IThundxr!)
- [x] [Allow crafting Waxed Copper Tile/Shingle stairs directly](https://github.com/Creators-of-Create/Create/pull/9801) (Thanks, techno-sam!)
- [x] [Fix Biomes We've Gone compatibility recipes](https://github.com/Creators-of-Create/Create/issues/9500)
- [x] [Fix the Bound Cardboard crafting recipe using `minecraft:string` instead of `forge:string`](https://github.com/Creators-of-Create/Create/issues/9501) (Thanks, IThundxr!)
- [x] [Fix Environmental compatibility recipes](https://github.com/Creators-of-Create/Create/pull/8471)

## Bugs Fixed
- [x] [Fix a precision error in TrackNodeLocation](https://github.com/Creators-of-Create/Create/issues/9509) (Thanks, IThundxr!)
- [x] [Fix BlockEntities in a VirtualRenderWorld not getting marked as removed](https://github.com/Creators-of-Create/Create/issues/9821) (Thanks, IThundxr!)
- [x] [Fix blocks destroyed by tree fertilizer not dropping](https://github.com/Creators-of-Create/Create/pull/9758) (Thanks, katietheqt!)
- [x] [Fix closing the Train Schedule menu by pressing E while typing](https://github.com/Creators-of-Create/Create/pull/9729) (Thanks, fywchis!)
- [x] [Fix Copycats dropping their items when replaced with a command](https://github.com/Creators-of-Create/Create/commit/efc3d3e5f589fb691dfa50b1c6ca3ad2791e1bbf) (Thanks, IThundxr!)
- [x] [Fix a bunch of other blocks dropping their items when replaced with a command](https://github.com/Creators-of-Create/Create/commit/3ba76bcefff6707afbcbad8e61fcedbdcff3ec24) (Thanks, IThundxr!)
- [x] [Fix duplicate readings from Stock Links on the same storage](https://github.com/Creators-of-Create/Create/pull/9793) (Thanks, techno-sam!)
- [x] [Fix Factory Gauges requesting packages on load](https://github.com/Creators-of-Create/Create/issues/8245) (Thanks, curryducker!)
- [x] [Fix Fluid Tanks not properly updating neighbors](https://github.com/Creators-of-Create/Create/pull/9814) (Thanks, techno-sam!)
- [x] [Fix players using Linked Controller Lecterns with their feet](https://github.com/Creators-of-Create/Create/issues/9922) (Thanks, IThundxr!)
- [x] [Fix quick-moving an item in an Attribute Filter moving the wrong slot](https://github.com/Creators-of-Create/Create/pull/9729) (Thanks, fywchis!)
- [x] [Fix seats not being bouncy](https://github.com/Creators-of-Create/Create/commit/6409450e032339057ac7166992f3134db972e49e) (Thanks, IThundxr!)
- [x] [Fix Steam Boilers not accepting water](https://github.com/Creators-of-Create/Create/pull/9813) (Thanks, techno-sam!)
- [x] [Fix the bottomless fluid threshold being over by 1](https://github.com/Creators-of-Create/Create/commit/ecbb4f20db4cd5224416b6415d384363af288543) (Thanks, IThundxr!)
- [x] [Fix the Mechanical Mixer continuing to Mix when power is cut](https://github.com/Creators-of-Create/Create/issues/6249) (Thanks, AyOhEe and IThundxr!)
- [x] [Respect macOS's interfaceScale for the Xaero's Train Map](https://github.com/Creators-of-Create/Create/pull/9736) (Thanks, SoprachevAK!)
- [x] [Fix conveyors losing items when they are unloaded](https://github.com/Creators-of-Create/Create/issues/9882) (Thanks, Apertyotis!)
- [x] [Fix toolboxes deleting items when reloaded](https://github.com/Creators-of-Create/Create/pull/9757) (Thanks, katietheqt)
- [x] [Fix incorrect rendering in the Wrench Rotation Menu with non-BlockEntity blocks, leading to those blocks not being rotateable](https://github.com/Creators-of-Create/Create/issues/9608) (Thanks, IThundxr!)
- [x] [Fix item capabilities being reset when serializing contraption inventories](https://github.com/VoidLeech/Repair/issues/10)

## Crashes Fixed
- [x] [Fix a crash when filling a Cauldron with a Spout](https://github.com/Creators-of-Create/Create/issues/9843) (Thanks, IThundxr!)
- [x] [Fix a crash when rendering certain BlockEntities on contraptions](https://github.com/Creators-of-Create/Create/issues/9459) (Thanks, IThundxr!)
- [x] [Fix Adjustable Chain Gearshifts crashing the game with certain inputs](https://github.com/Creators-of-Create/Create/pull/9803) (Thanks, techno-sam and Cyvack!)
- [x] [Fix a crash when a train is disassembled using CC:Tweaked](https://github.com/Creators-of-Create/Create/issues/9843) (Thanks, IThundxr!)
- [x] [Limit Train configs to prevent OOM](https://github.com/Creators-of-Create/Create/issues/9626) (Thanks, IThundxr!)
- [x] [Fix a crash in the CC:Tweaked lua scripts when setting the Display Link cursor position to a negative value](https://github.com/Creators-of-Create/Create/pull/9984) (Thanks, Fiona42069!)
- [x] [Fix a crash when using a Schematicannon to place a Linked Controller Lectern](https://github.com/Creators-of-Create/Create/issues/9448)
- [x] [Fix a crash when using the latest version of Galosphere](https://github.com/Creators-of-Create/Create/issues/10156)
- [x] [REGISTRATE] [Fix bucket- or block-less fluids crashing when requesting their bucket/block](https://github.com/tterrag1098/Registrate/commit/2fd7712817b10c0d9a218301235894ce8148edff) (Thanks, tterrag1098!)
- [x] [PONDER] [Fix thread-unsafe keybind access, including log spam when Ponder keybind is unbound](https://github.com/Creators-of-Create/Ponder/commit/551790b9363e1d5c01f48fd21b37b00c775484ff) (Thanks, IThundxr!)

## Tweaks
- [x] [Allow any block tagged `#create:seats` to be used as a seat on contraptions](https://github.com/Creators-of-Create/Create/pull/9907) (Thanks, ashbillzw!)
- [x] [Changes to `FluidPropagator` to support any pump extending `PumpBlock`](https://github.com/Creators-of-Create/Create/pull/9763) (Thanks, hadron13!)
- [x] [Prevent mushrooms being harvested while on Farmer's Delight Rich Soil](https://github.com/Creators-of-Create/Create/pull/9940) (Thanks, AyOhEe!)
- [x] [Add Plough blacklist and whitelist block tags: `#create:plough_blacklist`, `#create:plough_whitelist`](https://github.com/Creators-of-Create/Create/pull/9346)
- [x] [Remove limit on Mixer processing time](https://github.com/Creators-of-Create/Create/pull/10022) (Thanks, AyOhEe!)

## Dupes Fixed
- [x] [Fix a dupe when breaking a Postbox while its menu is open](https://github.com/Creators-of-Create/Create/pull/9802) (Thanks, techno-sam!)
- [x] [Fix a dupe with the Schematicannon ignoring BlockEntity item requirements](https://github.com/Creators-of-Create/Create/issues/9511) (Thanks, IThundxr!)
- [x] [Fix a dupe with the Deployer](https://github.com/Creators-of-Create/Create/commit/dd133f667969870dd6e9b96395097ac93cd91ced) (Thanks, IThundxr!)
