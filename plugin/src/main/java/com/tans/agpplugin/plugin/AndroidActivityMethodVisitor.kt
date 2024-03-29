package com.tans.agpplugin.plugin

import com.android.build.api.instrumentation.ClassContext
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes
import org.objectweb.asm.commons.AdviceAdapter

class AndroidActivityMethodVisitor(
    private val classContext: ClassContext,
    private val outputVisitor: MethodVisitor,
    access: Int,
    private val name: String,
    des: String
) : AdviceAdapter(
    ASM9,
    outputVisitor,
    access,
    name,
    des
) {
    override fun onMethodEnter() {
        super.onMethodEnter()
        Log.d(msg = "Hook method in: className=${classContext.currentClassData.className}, method=${name}")
        outputVisitor.visitMethodInsn(
            Opcodes.INVOKESTATIC,
            METHOD_IN_OUT_HOOK_CLASS_NAME,
            IN_HOOK_METHOD_NAME,
            IN_HOOK_METHOD_DES,
            false
        )
    }

    override fun onMethodExit(opcode: Int) {
        Log.d(msg = "Hook method out: className=${classContext.currentClassData.className}, method=${name}")
        outputVisitor.visitMethodInsn(
            Opcodes.INVOKESTATIC,
            METHOD_IN_OUT_HOOK_CLASS_NAME,
            OUT_HOOK_METHOD_NAME,
            OUT_HOOK_METHOD_DES,
            false
        )
        super.onMethodExit(opcode)
    }


    companion object {

        const val METHOD_IN_OUT_HOOK_CLASS_NAME = "com/tans/agpplugin/demo/MethodInOutHook"

        const val IN_HOOK_METHOD_NAME = "methodIn"
        const val IN_HOOK_METHOD_DES = "()V"

        const val OUT_HOOK_METHOD_NAME = "methodOut"
        const val OUT_HOOK_METHOD_DES = "()V"
    }
}