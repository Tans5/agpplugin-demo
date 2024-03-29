package com.tans.agpplugin.plugin

import com.android.build.api.instrumentation.AsmClassVisitorFactory
import com.android.build.api.instrumentation.ClassContext
import com.android.build.api.instrumentation.ClassData
import com.android.build.api.instrumentation.InstrumentationParameters
import org.objectweb.asm.ClassVisitor

abstract class AndroidActivityClassVisitorFactory : AsmClassVisitorFactory<InstrumentationParameters.None> {

    override fun createClassVisitor(
        classContext: ClassContext,
        nextClassVisitor: ClassVisitor
    ): ClassVisitor {
       return AndroidActivityClassVisitor(classContext, nextClassVisitor)
    }

    override fun isInstrumentable(classData: ClassData): Boolean {
        return classData.superClasses.contains("android.app.Activity")
    }
}