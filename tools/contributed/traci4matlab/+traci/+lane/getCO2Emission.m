function CO2Emission = getCO2Emission(laneID)
%getCO2Emission Returns the CO2 emission on the given lane.
%   CO2Emission = getCO2Emission(LANEID) Returns the CO2 emission in mg for
%   the last time step on the given lane.

%   Copyright 2015 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id$

import traci.constants
CO2Emission = traci.lane.getUniversal(constants.VAR_CO2EMISSION, laneID);