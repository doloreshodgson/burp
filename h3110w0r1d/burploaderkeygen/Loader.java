/*
 * Decompiled with CFR 0.152.
 */
package com.h3110w0r1d.burploaderkeygen;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;
import java.util.Iterator;
import jdk.internal.org.objectweb.asm.ClassReader;
import jdk.internal.org.objectweb.asm.ClassWriter;
import jdk.internal.org.objectweb.asm.tree.AbstractInsnNode;
import jdk.internal.org.objectweb.asm.tree.ClassNode;
import jdk.internal.org.objectweb.asm.tree.InsnList;
import jdk.internal.org.objectweb.asm.tree.InsnNode;
import jdk.internal.org.objectweb.asm.tree.IntInsnNode;
import jdk.internal.org.objectweb.asm.tree.JumpInsnNode;
import jdk.internal.org.objectweb.asm.tree.LabelNode;
import jdk.internal.org.objectweb.asm.tree.LdcInsnNode;
import jdk.internal.org.objectweb.asm.tree.MethodInsnNode;
import jdk.internal.org.objectweb.asm.tree.MethodNode;
import jdk.internal.org.objectweb.asm.tree.TypeInsnNode;
import jdk.internal.org.objectweb.asm.tree.VarInsnNode;

public class Loader
implements ClassFileTransformer {
    public Loader() {
        System.out.println("    ____                      __                    __             __ __\n   / __ )__  ___________     / /   ____  ____ _____/ /__  _____   / //_/__  __  ______ ____  ____\n  / __  / / / / ___/ __ \\   / /   / __ \\/ __ `/ __  / _ \\/ ___/  / ,< / _ \\/ / / / __ `/ _ \\/ __ \\\n / /_/ / /_/ / /  / /_/ /  / /___/ /_/ / /_/ / /_/ /  __/ /     / /| /  __/ /_/ / /_/ /  __/ / / /\n/_____/\\__,_/_/  / .___/  /_____/\\____/\\__,_/\\__,_/\\___/_/     /_/ |_\\___/\\__, /\\__, /\\___/_/ /_/\n                /_/                                                      /____//____/");
        System.out.println("Github: https://github.com/h3110w0r1d-y/BurpLoaderKeygen \u5546\u4e1a\u4f7f\u7528\u8bf7\u8d2d\u4e70\u6b63\u7248\u8f6f\u4ef6\uff01");
    }

    /*
     * Unable to fully structure code
     */
    public byte[] burp_patch2(String className, byte[] classBytes) {
        if (className.startsWith("burp/") == false) return null;
        if (classBytes.length < 110000) {
            return null;
        }
        cr = new ClassReader(classBytes);
        cn = new ClassNode();
        cr.accept(cn, 0);
        var5_5 = cn.methods.iterator();
        block0: while (true) {
            if (!var5_5.hasNext()) {
                writer = new ClassWriter(3);
                cn.accept(writer);
                return writer.toByteArray();
            }
            method = var5_5.next();
            if (!method.desc.equals("([Ljava/lang/Object;Ljava/lang/Object;)V") || method.instructions.size() <= 20000) continue;
            insnList = method.instructions;
            j = 0;
            i = insnList.size() - 1;
            while (true) {
                if (i > 0) ** break;
                continue block0;
                if (insnList.get(i) instanceof TypeInsnNode && (typeInsnNode = (TypeInsnNode)insnList.get(i)).getOpcode() == 187 && "java/lang/Exception".equals(typeInsnNode.desc) && ++j == 2) {
                    for (k = 0; k < 6; ++k) {
                        if (!(insnList.get(i - k) instanceof JumpInsnNode)) continue;
                        jumpInsnNode = (JumpInsnNode)insnList.get(i - k);
                        method.instructions.insert(insnList.get(i - k), new JumpInsnNode(167, jumpInsnNode.label));
                    }
                }
                --i;
            }
            break;
        }
    }

    /*
     * Unable to fully structure code
     */
    public byte[] bounty_patch(String className, byte[] classBytes) {
        if (!className.equals("feign/okhttp/OkHttpClient")) {
            return null;
        }
        cr = new ClassReader(classBytes);
        cn = new ClassNode();
        cr.accept(cn, 0);
        methods = cn.methods;
        var7 = methods.iterator();
        block0: while (true) {
            if (!var7.hasNext()) {
                writer = new ClassWriter(3);
                cn.accept(writer);
                return writer.toByteArray();
            }
            method = var7.next();
            if (!"toFeignResponse".equals(method.name) || !"(Lokhttp3/Response;Lfeign/Request;)Lfeign/Response;".equals(method.desc)) continue;
            srcList = method.instructions;
            var10_10 = insnNodes = srcList.toArray();
            var11_11 = var10_10.length;
            var12_12 = 0;
            while (true) {
                if (var12_12 < var11_11) ** break;
                continue block0;
                insnNode = var10_10[var12_12];
                if (insnNode instanceof MethodInsnNode && insnNode.getOpcode() == 182) {
                    methodInsnNode = (MethodInsnNode)insnNode;
                    if (methodInsnNode.owner.equals("feign/Response$Builder") && methodInsnNode.name.equals("build") && methodInsnNode.desc.equals("()Lfeign/Response;")) {
                        insnList = new InsnList();
                        insnList.add(new VarInsnNode(25, 1));
                        insnList.add(new MethodInsnNode(182, "feign/Request", "url", "()Ljava/lang/String;", false));
                        insnList.add(new VarInsnNode(25, 1));
                        insnList.add(new MethodInsnNode(182, "feign/Request", "body", "()[B", false));
                        insnList.add(new MethodInsnNode(184, "com/h3110w0r1d/burploaderkeygen/Filter", "BountyFilter", "(Ljava/lang/String;[B)[B", false));
                        insnList.add(new VarInsnNode(58, 2));
                        insnList.add(new VarInsnNode(25, 2));
                        outLabel = new LabelNode();
                        insnList.add(new JumpInsnNode(198, outLabel));
                        insnList.add(new VarInsnNode(25, 2));
                        insnList.add(new MethodInsnNode(182, "feign/Response$Builder", "body", "([B)Lfeign/Response$Builder;", false));
                        insnList.add(new IntInsnNode(17, 200));
                        insnList.add(new MethodInsnNode(182, "feign/Response$Builder", "status", "(I)Lfeign/Response$Builder;", false));
                        insnList.add(outLabel);
                        srcList.insertBefore((AbstractInsnNode)methodInsnNode, insnList);
                    }
                }
                ++var12_12;
            }
            break;
        }
    }

    public byte[] bigint_patch(String className, byte[] classBytes) {
        if (!className.equals("java/math/BigInteger")) {
            return null;
        }
        try {
            MethodNode mn;
            ClassReader reader = new ClassReader(classBytes);
            ClassNode node = new ClassNode();
            reader.accept(node, 0);
            Iterator<MethodNode> iterator = node.methods.iterator();
            do {
                if (!iterator.hasNext()) return null;
                mn = iterator.next();
            } while (!"oddModPow".equals(mn.name) || !"(Ljava/math/BigInteger;Ljava/math/BigInteger;)Ljava/math/BigInteger;".equals(mn.desc));
            InsnList instructions = new InsnList();
            instructions.add(new LdcInsnNode("784494550650629402578318812038873246103062429392765900863679692401515917483288444551360396993891243581323776031174104716520618196517054075399181152805062167863399993397788108526062574442957397380276947009110814837620187732300913174633871838721345699310979475106228455540662931554176848428161559382213664590949585029191542943085957823265503304336742766067624625736735254354398945046006311244707353417137449868938968730226771591246625865947893595622114503547108373009472960599444147060111971350419883686667465293379976619008764355965580465504280432683151160078556566540083447715822702727667177022111431814347374423107231482768994558297231501807470318213348545623616323683043933536716592761352026200587983508688675808140761791235890894421345168268944272003766511723863509248901907706583412647586304919169213119463168988654772466925970559627610723088498856098005959331457878486945150019623509488580895386399262569844097131075786500942530443065538693113151751368621487717116172049865065084334155464496258411943450302953590604940950912787049251686884076034812543594664806687830000981320979025230888404872529342183959392375396232582878580953197200612251616917416861184388805361800660771481139764435258505831114252491932487115517415875364419"));
            instructions.add(new VarInsnNode(25, 2));
            instructions.add(new MethodInsnNode(182, "java/math/BigInteger", "toString", "()Ljava/lang/String;", false));
            instructions.add(new MethodInsnNode(182, "java/lang/String", "equals", "(Ljava/lang/Object;)Z", false));
            LabelNode label = new LabelNode();
            instructions.add(new JumpInsnNode(153, label));
            instructions.add(new TypeInsnNode(187, "java/math/BigInteger"));
            instructions.add(new InsnNode(89));
            instructions.add(new LdcInsnNode("772466485993532616265602004787593990571998354762769517556824947462364733846988896764791786418284048308408035898123800088385145599914482866982121337242267111996730589833008965586904619624210600143815034555107626281306790690247380277401223658213686836260051740513369525229945146545746232288644834011082272373923840674183841152721282934896147636659685196110193170859036230613942473057350558696079781329724723538612065451271117946653171891463701206005132474249143377280956637274812017722966962038177658826682928177419967120615723412715280627140352666513761887655683881127941062521608622150057375302773817128043341551104335925307524362463158532014345920401529131689972511711145366384313357331219918007902162272947810092434401421074436297130543420857679173250879221703239461167245128718027210585470097741338476167664231386224393829173753597329684981549442031004886309176804274536524421269896699613148427017011648805968328213879561704664823249805341430850708344955922886822191380206543393525391519722222586339612770531074577303349625790208047047035933996137831086461496793235410445769659982355567180073509405077094784975713575937671560399734544873967339281900443458904052345698752817504449813508664835247206727493828740500751727305948870197"));
            instructions.add(new MethodInsnNode(183, "java/math/BigInteger", "<init>", "(Ljava/lang/String;)V", false));
            instructions.add(new VarInsnNode(58, 2));
            instructions.add(label);
            mn.instructions.insert(instructions);
            ClassWriter writer = new ClassWriter(3);
            node.accept(writer);
            return writer.toByteArray();
        }
        catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    public byte[] burp_patch1(String className, byte[] classBytes) {
        if (!className.startsWith("burp/")) return null;
        if (classBytes.length <= 110000) return null;
        ClassReader cr = new ClassReader(classBytes);
        ClassNode cn = new ClassNode();
        cr.accept(cn, 0);
        Iterator<MethodNode> iterator = cn.methods.iterator();
        while (true) {
            if (!iterator.hasNext()) {
                ClassWriter writer = new ClassWriter(3);
                cn.accept(writer);
                return writer.toByteArray();
            }
            MethodNode method = iterator.next();
            if (!method.desc.equals("([Ljava/lang/Object;Ljava/lang/Object;)V") || method.instructions.size() <= 20000) continue;
            InsnList insnList = method.instructions;
            insnList.clear();
            insnList.add(new VarInsnNode(25, 0));
            insnList.add(new MethodInsnNode(184, "com/h3110w0r1d/burploaderkeygen/Filter", "BurpFilter", "([Ljava/lang/Object;)V", false));
            insnList.add(new InsnNode(177));
            method.exceptions.clear();
            method.tryCatchBlocks.clear();
        }
    }

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classBytes) {
        byte[] result = this.bigint_patch(className, classBytes);
        if (result != null) {
            return result;
        }
        result = this.bounty_patch(className, classBytes);
        if (result != null) {
            return result;
        }
        result = this.burp_patch1(className, classBytes);
        if (result != null) {
            return result;
        }
        result = this.burp_patch2(className, classBytes);
        return result;
    }

    public static void premain(String agentArgs, Instrumentation inst) {
        Loader loader = new Loader();
        inst.addTransformer(loader);
    }
}
