package com.softrice.systrace


import org.gradle.api.GradleException
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * Created by zhangshaowen on 17/6/16.
 */
class SystracePlugin implements Plugin<Project> {
    private static final String TAG = "SystracePlugin"

    @Override
    void apply(Project project) {
        project.extensions.create("systrace", com.softrice.systrace.extension.SystraceExtension)

        if (!project.plugins.hasPlugin('com.android.application')) {
            throw new GradleException('Systrace Plugin, Android Application plugin required')
        }

        project.getDependencies().add('implementation', 'com.softrice.systrace:tracetag:1.0.0')

        project.afterEvaluate {
            def android = project.extensions.android
            def configuration = project.systrace
            android.applicationVariants.all { variant ->

                String output = configuration.output
                if (Util.isNullOrNil(output)) {
                    configuration.output = project.getBuildDir().getAbsolutePath() + File.separator + "systrace_output"
                    Log.i(TAG, "set Systrace output file to " + configuration.output)
                }

                Log.i(TAG, "Trace enable is %s", configuration.enable)
                if (configuration.enable) {
                    com.softrice.systrace.transform.SystemTraceTransform.inject(project, variant)
                }
            }
        }
    }
}
