function speed = getSpeed(vehID)
%getSpeed Get the vehicle speed.
%   speed = getSpeed(VEHID) Returns the speed in m/s of the named vehicle 
%   within the last step.

%   Copyright 2016 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: getSpeed.m 31 2016-09-28 15:16:56Z afacostag $

import traci.constants
speed = traci.vehicle.getUniversal(constants.VAR_SPEED, vehID);