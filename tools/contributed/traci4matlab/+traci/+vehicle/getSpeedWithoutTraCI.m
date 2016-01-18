function speedWithoutTraCI = getSpeedWithoutTraCI(vehID)
%   Copyright 2015 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: getSpeedWithoutTraCI.m 20 2015-03-02 16:52:32Z afacostag $

import traci.constants
speedWithoutTraCI = traci.vehicle.getUniversal(constants.VAR_SPEED_WITHOUT_TRACI, vehID);