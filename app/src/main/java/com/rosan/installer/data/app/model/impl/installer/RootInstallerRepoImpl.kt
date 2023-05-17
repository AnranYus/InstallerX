package com.rosan.installer.data.app.model.impl.installer

import android.content.Context
import android.os.IBinder
import com.rosan.app_process.AppProcess
import com.rosan.installer.data.app.model.entity.InstallEntity
import com.rosan.installer.data.app.model.entity.InstallExtraEntity
import com.rosan.installer.data.settings.model.room.entity.ConfigEntity
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

object RootInstallerRepoImpl : IBinderInstallerRepoImpl(), KoinComponent {
    private val context by inject<Context>()

    private lateinit var process: AppProcess

    override suspend fun doWork(
        config: ConfigEntity,
        entities: List<InstallEntity>,
        extra: InstallExtraEntity
    ) {
        process = AppProcess.Root()
        process.init(context.packageName)
        try {
            super.doWork(config, entities, extra)
        } finally {
            process.close()
        }
    }

    override suspend fun iBinderWrapper(iBinder: IBinder): IBinder {
        return process.binderWrapper(iBinder)
    }
}