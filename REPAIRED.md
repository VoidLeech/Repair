# Create Repair's Big List of Backports
This is a list of bug fixes, crash fixes, recipes, visuals, tweaks, optimizations, and dupe fixes that Create Repair has backported.

## Optimizations
- [x] [Significantly optimize Funnel extraction and MountedItemStorageWrapper](https://github.com/Creators-of-Create/Create/issues/9706) (Thanks, PoppyBlossom!)

## Visual Fixes
- [x] [Correct the spelling of "Assember"](https://github.com/Creators-of-Create/Create/pull/9652) (Thanks, PoppyBlossom!)
- [x] [Correct the spelling of "Restone" and "Amethust"](https://github.com/Creators-of-Create/Create/pull/9658) (Thanks, PoppyBlossom!)
- [x] [Fix Backtanks not having the enchantment glint when worn](https://github.com/Creators-of-Create/Create/commit/79b5d3b37e2d1970818dd97ca460b649cd0a456c) (Thanks, IThundxr!)
- [x] [Fix emissive textures in copycats](https://github.com/Creators-of-Create/Create/issues/9675) (Thanks, IThundxr!)
- [x] [Fix incorrect rendering in the Wrench Rotation Menu with non-BlockEntity blocks](https://github.com/Creators-of-Create/Create/issues/9608) (Thanks, CKenJa!)
- [x] [Fix the Encased Fan shaft's texture being rotated incorrectly](https://github.com/Creators-of-Create/Create/commit/ecbb4f20db4cd5224416b6415d384363af288543) (Thanks, IThundxr!)
- [x] [Fix upright item renderers using per-tick position instead of camera position](https://github.com/Creators-of-Create/Create/pull/9942) (Thanks, Vercte!)

## Recipe Tweaks
- [x] [Adjust the Clay crushing recipe to always give 4 clay balls](https://github.com/Creators-of-Create/Create/commit/2533d6933eed770f6d91de53c8f67b8a9633d24c) (Thanks, IThundxr!)
- [x] [Allow crafting Waxed Copper Tile/Shingle stairs directly](https://github.com/Creators-of-Create/Create/pull/9801) (Thanks, techno-sam!)
- [x] [Fix Biomes We've Gone compatibility recipes](https://github.com/Creators-of-Create/Create/issues/9500) (Thanks, VoidLeech!)
- [x] [Fix the Bound Cardboard crafting recipe using `minecraft:string` instead of `forge:string`](https://github.com/Creators-of-Create/Create/issues/9501) (Thanks, IThundxr!)

## Bugs Fixed
- [x] [Fix a precision error in TrackNodeLocation](https://github.com/Creators-of-Create/Create/issues/9509) (Thanks, IThundxr!)
- [x] [Fix BlockEntities in a VirtualRenderWorld not getting marked as removed](https://github.com/Creators-of-Create/Create/issues/9821) (Thanks, IThundxr!)
- [x] [Fix blocks destroyed by tree fertilizer not dropping](https://github.com/Creators-of-Create/Create/pull/9758) (Thanks, katietheqt!)
- [x] [Fix closing the Train Schedule menu by pressing E while typing](https://github.com/Creators-of-Create/Create/pull/9729) (Thanks, fywchis!)
- [x] [Fix Copycats dropping their items when replaced with a command](https://github.com/Creators-of-Create/Create/commit/efc3d3e5f589fb691dfa50b1c6ca3ad2791e1bbf) (Thanks, IThundxr!)
- [x] [Fix duplicate readings from Stock Links on the same storage](https://github.com/Creators-of-Create/Create/pull/9793) (Thanks, techno-sam!)
- [x] [Fix Factory Gauges requesting packages on load](https://github.com/Creators-of-Create/Create/issues/8245) (Thanks, curryducker!)
- [x] [Fix Fluid Tanks not properly updating neighbors](https://github.com/Creators-of-Create/Create/pull/9814) (Thanks, techno-sam!)
- [x] [Fix hand crank rotation visuals](https://github.com/Creators-of-Create/Create/pull/8828) (Thanks, nopeless!)
- [x] [Fix players using Linked Controller Lecterns with their feet](https://github.com/Creators-of-Create/Create/issues/9922) (Thanks, IThundxr!)
- [x] [Fix quick-moving an item in an Attribute Filter moving the wrong slot](https://github.com/Creators-of-Create/Create/pull/9729) (Thanks, fywchis!)
- [x] [Fix seats not being bouncy](https://github.com/Creators-of-Create/Create/commit/6409450e032339057ac7166992f3134db972e49e) (Thanks, IThundxr!)
- [x] [Fix Steam Boilers not accepting water](https://github.com/Creators-of-Create/Create/pull/9813) (Thanks, techno-sam!)
- [x] [Fix the bottomless fluid threshold being over by 1](https://github.com/Creators-of-Create/Create/commit/ecbb4f20db4cd5224416b6415d384363af288543) (Thanks, IThundxr!)
- [x] [Fix the Mechanical Mixer continuing to Mix when power is cut](https://github.com/Creators-of-Create/Create/issues/6249) (Thanks, AyOhEe and IThundxr!)
- [x] [Increase epsilon of the RotationPropagator speed comparison](https://github.com/Creators-of-Create/Create/pull/10013) (Thanks, Cyvack!)
- [x] [Respect macOS's interfaceScale for the Xaero's Train Map](https://github.com/Creators-of-Create/Create/pull/9736) (Thanks, SoprachevAK!)
- [x] [Using a Schematicannon to place a Linked Controller Lectern no longer crashes](https://github.com/Creators-of-Create/Create/issues/9448)


## Crashes Fixed
- [x] [Fix a crash when filling a Cauldron with a Spout](https://github.com/Creators-of-Create/Create/issues/9843) (Thanks, IThundxr!)
- [x] [Fix a crash when rendering BlockEntities on contraptions](https://github.com/Creators-of-Create/Create/issues/9459) (Thanks, IThundxr!)
- [x] [Fix Adjustable Chain Gearshifts crashing the game with certain inputs](https://github.com/Creators-of-Create/Create/pull/9803) (Thanks, techno-sam!)
- [x] [Fix CC:Tweaked crashing when a train is disassembled by a computer](https://github.com/Creators-of-Create/Create/issues/9843) (Thanks, IThundxr!)
- [x] [Limit Train configs to prevent an OOM](https://github.com/Creators-of-Create/Create/issues/9626) (Thanks, IThundxr!)
- [x] [Setting the Display Link cursor position to a negative value using CC:Tweaked causes a crash](https://github.com/Creators-of-Create/Create/pull/9984) (Thanks, Fiona42069!)
- [x] [REGISTRATE] [Fix bucket-less fluids crashing](https://github.com/tterrag1098/Registrate/commit/2fd7712817b10c0d9a218301235894ce8148edff) (Thanks, tterrag1098!)

## Tweaks
- [x] [Allow any block tagged `#create:seats` to be used as a seat on contraptions](https://github.com/Creators-of-Create/Create/pull/9907) (Thanks, ashbillzw!)
- [x] [Changes to `FluidPropagator` to support any pump extending `PumpBlock`](https://github.com/Creators-of-Create/Create/pull/9763) (Thanks, hadron13!)
- [x] [Prevent mushrooms being harvested while on Farmer's Delight Rich Soil](https://github.com/Creators-of-Create/Create/pull/9940) (Thanks, AyOhEe!)

## Dupes Fixed
- [x] [Fix a dupe when breaking a Postbox while its menu is open](https://github.com/Creators-of-Create/Create/pull/9802) (Thanks, techno-sam!)
- [x] [Fix a dupe when placing Placards with a Schematicannon](https://github.com/Creators-of-Create/Create/issues/9511) (Thanks, IThundxr!)
- [x] [Fix a dupe with the Deployer](https://github.com/Creators-of-Create/Create/commit/dd133f667969870dd6e9b96395097ac93cd91ced) (Thanks, IThundxr!)
