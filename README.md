# Create Repair: The Unofficial Create Community Patch

What's this? One word verb, Create style logo, yet another boring addon?
I mean yes, we don't add anything user-facing*; if we're boring that means we're doing our job. It's also a love letter to the mod that brought me back into modded Minecraft and got me making mods; my repertoire mostly consists of bugfixes. Not vanilla bugs: bugs in mods I love, but that are unfortunately no longer maintained.
And this mod is no different. Create may have dropped support for 1.20.1, but give me the keys to the kingdom, and this version lives forever.

So what do we do?
1. Fix 1.20.1-exclusive bugs.
2. Backport Create's fixes for bugs that also affected 1.20.1.
3. Backport new API.
4. *Backport applicable compat recipes. Oops, it's user-facing after all.

All of the above are subject to the difficulty of actually implementing them, taking into account the invasiveness of the required changes: no update to Create Repair should break an addon, lest we saddle either Create, the addon, or both, with issue reports.

For a full list of our backported fixes, tweaks, and more, check out our [Big List of Backports](https://github.com/VoidLeech/Repair/blob/mc1.20.1/forge/dev/REPAIRED.md).

Some bugs require Fabric Mixin to fix, which for Forge can be provided by either Mixinbooster (recommended) or Sinytra Connector. As such, there is no hard dependency on either, but those fixes will be disabled if neither is present.

### Contributing
There are three ways developers can assist with this project:
- Submit fixes for bugs in our code (target the Forge branch unless the change is specific to Fabric).
- Backport PRs or commits from Create (please try to list the original author as co-author of your commit).
- For bugs still present on 1.21.1+ Create, submit PRs to Create itself (we'll see them) and then, optionally, to us.

Fabric requires Java 21 to build. LLM contributions will be rejected.

### F.A.Q.
**Q: Can this be used in my modpack?**  
**A:** Yes, obviously.

**Q: Does this conflict with other mods backporting specific fixes?**  
**A:** It shouldn't. Throughout our (admittedly slow to get off the ground) initial development we've become aware of other mods that backported specific fixes (and got those released earlier). Should those mods be present, our implementation of the fix will not be applied, and we use relatively compatible mixins in case we missed one. If we've missed one where it results in incorrect behaviour (or worse), do please let us know.

**Q: Will you port this to Minecraft version X.Y.Z?**  
**A:** We might start a 1.21.1 branch when Create drops support for that eventually. Otherwise, no, We're simply too far behind for that to be viably maintained.

**Q: Will you backport {feature from Create's next big content update}?**  
**A:** No.

### Licensing
This project is released under the [MIT license](LICENSE).  
[FluiderBuilderMixin](src/main/java/ch/voidlee/repair/mixin/FluidBuilderMixin.java) is released under the [MPL-2.0 license](other-licenses/mpl_registrate)

#### Third-Party Content
By its very nature, this project is going to be based on and contain parts of Create, whose code is licensed under the [MIT license](other-licenses/mit_create).  
FluiderBuilderMixin (see above) is based on a commit to Registrate and thus licensed accordingly.  
[OptionsMixin](src/main/java/ch/voidlee/repair/mixin/client/OptionsMixin.java) is based on Fabric API's GameOptionsMixin, which is licensed under the [Apache 2.0 license](other-licenses/apache_fabric)

This project is an addon, though we treat it much like a fork. As such, we try to credit the contributors whose fixes we backport; aside from being in mixin form, their patches are almost applied 1:1, and the project wouldn't be possible without them. Having said that, such credit, typically expressed as co-authorship on a commit, does not actually mean that they have done any work specifically for our project, and shouldn't be seen as them endorsing our work.