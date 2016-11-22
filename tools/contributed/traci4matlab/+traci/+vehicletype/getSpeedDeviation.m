function speedDeviation = getSpeedDeviation(typeID)
%getSpeedDeviation Returns the maximum speed deviation of vehicles of this type.
%   speedDeviation = getSpeedDeviation(TYPEID) Returns the maximum speed 
%   deviation of vehicles of this type.

%   Copyright 2016 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: getSpeedDeviation.m 31 2016-09-28 15:16:56Z afacostag $

import traci.constants
speedDeviation = traci.vehicletype.getUniversal(constants.VAR_SPEED_DEVIATION, typeID);