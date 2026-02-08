This is an incomplete list of issues, pull requests or commits that fix bugs (or are 1.20.1 exclusive bugs)

## Dupes / Inverse Dupes
- [ ] Infinite Money Glitch with the Placard and schematics https://github.com/Creators-of-Create/Create/issues/9511
- [ ] Postbox dupes if broken while menu open https://github.com/Creators-of-Create/Create/pull/9802

## Optimization
- [ ] Funnel Extraction, MountedItemStorageWrapper https://github.com/Creators-of-Create/Create/issues/9706

## Recipes
- [ ] BWG of https://github.com/Creators-of-Create/Create/issues/9500
- [ ] Use c:strings in Bound Cardboard recipe https://github.com/Creators-of-Create/Create/issues/9501
- [ ] Fully-efficient clay recipe https://github.com/Creators-of-Create/Create/commit/2533d6933eed770f6d91de53c8f67b8a9633d24c
- [ ] Crafting using waxed blocks https://github.com/Creators-of-Create/Create/pull/9801

## Easy
- [ ] Track Node imprecision https://github.com/Creators-of-Create/Create/issues/9509
- [ ] Factory gauges do not check the network on load https://github.com/Creators-of-Create/Create/issues/8245
- [ ] Cauldron crashes upon getting filled by Spout https://github.com/Creators-of-Create/Create/issues/9547
- [ ] Respect interfaceScale for Xaero Train Map https://github.com/Creators-of-Create/Create/pull/9736
- [ ] Fix Attribute Filter insertion https://github.com/Creators-of-Create/Create/pull/9729
- [ ] Make pressing `E` not close the Train Schedule menu when typing https://github.com/Creators-of-Create/Create/pull/9735
- [ ] Fix empty tanks sometimes not being fillable https://github.com/Creators-of-Create/Create/pull/9814
- [ ] Fix steam engine not filling with water https://github.com/Creators-of-Create/Create/pull/9813
- [ ] Limit max train size to prevent OOM https://github.com/Creators-of-Create/Create/issues/9626
- [ ] BE setRemoved not called https://github.com/Creators-of-Create/Create/issues/9821
- [ ] Lectern Controller interaction range https://github.com/Creators-of-Create/Create/issues/9922
- [ ] Mixers still mixing at low/zero speed https://github.com/Creators-of-Create/Create/issues/6249
  
## Medium
- [ ] RadialWrenchHandler visual glitches with non-BlockEntity blocks https://github.com/Creators-of-Create/Create/issues/9608
- [ ] Copycats do not render emissives correctly https://github.com/Creators-of-Create/Create/issues/9675
- [ ] Redstone Links & Nixie Tubes read redstone signals from the incorrect block face https://github.com/Creators-of-Create/Create/issues/8734
- [ ] CC Tweaked Train Disassembly Crashing https://github.com/Creators-of-Create/Create/issues/9843

## Hard
- [ ] [#9459](https://github.com/Creators-of-Create/Create/issues/9459 ) uses a hacky mixin fix to never call setchanged on the client;
it's simple but it's gross and might miss edge cases. the proper fix presumably requires asm to replace the class

## Unsorted
- [ ] Adjustable Chain Gearshifts crashing due to float imprecision https://github.com/Creators-of-Create/Create/pull/9803
- [ ] Bouncy Seats, but also some other unlabeled fixes https://github.com/Creators-of-Create/Create/commit/6409450e032339057ac7166992f3134db972e49e
- [ ] Encased Fan shaft texture fix, Train Disassembly with CC Computer attached fixed, Fix Bottomless Limit being over by 1 https://github.com/Creators-of-Create/Create/commit/ecbb4f20db4cd5224416b6415d384363af288543
- [ ] Fix copycat blocks dropping items when /fill'd https://github.com/Creators-of-Create/Create/commit/efc3d3e5f589fb691dfa50b1c6ca3ad2791e1bbf
- [ ] Contraption collision optimization? Will really depend on whether that's safely doable.

## Done!
- [x] https://github.com/Creators-of-Create/Create/pull/9763
- [x] https://github.com/Creators-of-Create/Create/pull/9658
- [x] https://github.com/Creators-of-Create/Create/pull/9652
- [x] https://github.com/Creators-of-Create/Create/issues/9448
- [x] https://github.com/Creators-of-Create/Create/commit/dd133f667969870dd6e9b96395097ac93cd91ced
- [x] https://github.com/Creators-of-Create/Create/issues/9459
- [x] https://github.com/Creators-of-Create/Create/pull/9793
- [x] https://github.com/Creators-of-Create/Create/commit/79b5d3b37e2d1970818dd97ca460b649cd0a456c
- [x] Destroy blocks when growing tree with fertilizer https://github.com/Creators-of-Create/Create/pull/9758