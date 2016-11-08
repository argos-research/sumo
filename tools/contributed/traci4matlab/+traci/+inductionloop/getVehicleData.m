function vehicleData = getVehicleData(loopID)
%getVehicleData Get several data about passed vehicles.
%   vehicleData = getVehicleData(LOOPID) Returns a cell structure 
%   containing several information about vehicles which passed the detector.

%   Copyright 2016 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: getVehicleData.m 31 2016-09-28 15:16:56Z afacostag $

import traci.constants
vehicleData = traci.inductionloop.getUniversal(constants.LAST_STEP_VEHICLE_DATA, loopID);