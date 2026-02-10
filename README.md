# Create Repair: The Unofficial Create Community Patch

What's this? One word verb, Create style logo, yet another boring addon?
I mean yes, we don't add anything user-facing*; if we're boring that means we're doing our job. It's also a love letter to the mod that brought me back into modded Minecraft and got me making mods; my repertoire mostly consists of bugfixes. Not vanilla bugs: bugs in mods I love, but that are unfortunately no longer maintained.
And this mod is no different. Create may have dropped support for 1.20.1, but give me the keys to the kingdom, and this version lives forever.

So what do we do?
1. Fix 1.20.1-exclusive bugs.
2. Backport Create's fixes for bugs that also affected 1.20.1.
3. Backport new API.
4. *Backport applicable compat recipes. Oops user-facing after all.

All of the above are subject to the difficulty of actually implementing them, taking into account the invasiveness of the required changes: no update to Repair should break an addon, lest we saddle either Create, the addon, or both, with issue reports.

### F.A.Q.
**Q: Can this be used in my modpack?**  
**A:** Yes, obviously.

**Q: Will you port this to Minecraft version X.Y.Z?**  
**A:** We might start a 1.21.1 branch when Create drops support for that eventually. Otherwise, no, We're simply too far behind for that to be viably maintained.

**Q: Will you backport {feature from Create's next big content update}?**  
**A:** No.

### Licensing
This project is released under the [MIT license](LICENSE).
[FluiderBuilderMixin](src/main/java/ch/voidlee/repair/mixin/FluidBuilderMixin.java) is released under the [MPL-2.0 license](other-licenses/mpl_registrate)

#### Third-Party Content
By its very nature, this project is going to be based on and contain parts of Create, whose code is licensed under the [MIT license](other-licenses/mit-create).
FluiderBuilderMixin (see above) is based on a commit to Registrate and thus licensed accordingly.